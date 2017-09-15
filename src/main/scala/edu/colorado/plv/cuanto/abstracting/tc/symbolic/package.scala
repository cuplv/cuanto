package edu.colorado.plv.cuanto
package abstracting.tc

import smtlib.Interpreter
import smtlib.interpreters.Z3Interpreter
import smtlib.parser.Commands._
import smtlib.parser.CommandsResponses.GetModelResponseSuccess
import smtlib.parser.Terms._
import smtlib.theories.Core.Not

import Semilattice._
import Abstraction._

package object symbolic {

  type Constraint[S] = S => Term
  type Transformer[S] = (S,S) => Term

  trait Symbol[S] {
    def draw(s: String): (S,Traversable[Command])
  }
  object Symbol {
    def apply[A : Symbol]: Symbol[A] = implicitly[Symbol[A]]
  }

  /** Create a [[smtlib.parser.terms.Term `Term`]] from a
    * [[smtlib.parser.terms.SSymbol `SSymbol`]] */
  val sTerm: SSymbol => QualifiedIdentifier =
    s => QualifiedIdentifier(SimpleIdentifier(s))

  trait SMTVal[V] {
    def interpret(value: Term): Option[V]
  }
  object SMTVal {
    def apply[A : SMTVal]: SMTVal[A] = implicitly[SMTVal[A]]
  }

  trait Model[C] {
    type Schema
    def getModel(name: String, interpreter: Interpreter): Option[C]
  }
  object Model {
    def apply[A : Model]: Model[A] = implicitly[Model[A]]
  }

  def model[C,S : Symbol](
    name: String, s: Constraint[S]
  )(
    implicit iM: Model[C] {type Schema = S}
  ): Option[C] = {
    val (schemaVal,decl) = Symbol[S].draw(name)
    val z3 = Z3Interpreter.buildDefault
    val assertion = Seq(Assert(s(schemaVal)))

    (decl ++ assertion ++ Seq(CheckSat())).map(z3.eval)

    Model[C].getModel(name, z3)
  }

  trait SymAbstract[A,S] {
    def gammaHat(a: A): Constraint[S]
  }
  object SymAbstract {
    def apply[A,S](implicit inst: SymAbstract[A,S]): SymAbstract[A,S] =
      implicitly[SymAbstract[A,S]]
  }

  def modelT[C,S](
    t: Transformer[S]
  )(
    pre: Constraint[S],
    post: Constraint[S]
  )(
    implicit iM: Model[C] {type Schema = S}
  ): Option[(C,C)] = ???

  def postHatUp[C,A : Semilattice,S : Symbol](t: Transformer[S])(v: A)(
    implicit iM: Model[C] {type Schema = S}, iS: SymAbstract[A,S], iA: Abstraction[C,A]
  ): A = {
    val notGammaHat = (e: A) => (l: S) => Not(iS.gammaHat(e)(l))
    def recur(low: A): A =
      modelT(t)(iS.gammaHat(v),notGammaHat(low)) match {
        case Some((s1,s2)) => recur(join(low,beta(s2)))
        case None => low
      }
    recur(bot[A])
  }

  def comprehend[V : SMTVal](m: List[SExpr]): Map[SSymbol,V] = {
    val p: (Map[SSymbol,V],SExpr) => Map[SSymbol,V] = {
      (acc,i) => i match {
        case DefineFun(FunDef(id,_,_,v)) => SMTVal[V].interpret(v) match {
          case Some(v) => acc + (id -> v)
          case None => acc
        }
        case _ => acc
      }
    }
    m.foldLeft[Map[SSymbol,V]](Map.empty)(p)
  }

  def getModelMap[V : SMTVal](i: Interpreter): Option[Map[SSymbol,V]] =
    i.eval(GetModel()) match {
      case GetModelResponseSuccess(m) => Some(comprehend(m))
      case _ => None
    }
}
