package edu.colorado.plv.cuanto
package abstracting.tc

import smtlib.parser.Commands.Command
import smtlib.parser.Terms.Term
import smtlib.theories.Core.Not

import Semilattice._
import Abstraction._

package object symbolic {

  type Constraint[S] = S => Term
  type Transformer[S] = (S,S) => Term

  trait Symbol[S] {
    def draw(s: String): (S,Traversable[Command])
    val idConstraint: Constraint[S]
  }

  trait Model[C] {
    type Schema
    def model(s: Constraint[Schema]): Option[C]
  }

  trait SymAbstract[A,S] {
    def gammaHat(a: A): Constraint[S]
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
}
