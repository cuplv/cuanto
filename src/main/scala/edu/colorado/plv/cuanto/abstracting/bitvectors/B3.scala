package edu.colorado.plv.cuanto.abstracting

package bitvectors

import scala.collection.immutable.{Map => IMap}

import smtlib._
import interpreters.Z3Interpreter
import theories.Core._
import theories.Constructors._
import parser.Terms._
import parser.Commands._
import parser.CommandsResponses._

import symbolic._

/** The concrete form, a tuple of three booleans */
case class B3(bool1: Boolean, bool2: Boolean, bool3: Boolean)

object B3 {
  val postHatUpInst =
    (t: (SMT3,SMT3) => Term, v: Vote3) => postHatUp(
      SMT3.model _,
      Vote3.represent _,
      SMT3.gammaHat _,
      t,
      not,
      v
    )

  /** The symbolic form, capable of fully representing a B3 as an SMT
    * script */
  case class SMT3(bool1: Term, bool2: Term, bool3: Term)
  
  object SMT3 {
  
    def model(
      s1: (SMT3 => Term),
      t: ((SMT3,SMT3) => Term),
      s2: (SMT3 => Term)
    ): Option[(B3,B3)] = {
      val a1 = SSymbol("a1")
      val b1 = SSymbol("b1")
      val c1 = SSymbol("c1")
      val a2 = SSymbol("a2")
      val b2 = SSymbol("b2")
      val c2 = SSymbol("c2")
  
      val mkTerm = (s: SSymbol) => QualifiedIdentifier(SimpleIdentifier(s))
      val mkSMT3 = (a: SSymbol,b: SSymbol,c: SSymbol) =>
      SMT3(mkTerm(a),mkTerm(b),mkTerm(c))
  
      val z3 = Z3Interpreter.buildDefault
  
      val smt1 = mkSMT3(a1,b1,c1)
      val smt2 = mkSMT3(a2,b2,c2)

      println(s1(smt1))
      println(s2(smt2))
      println(t(smt1,smt2))
  
      Seq(
        DeclareConst(a1,BoolSort()),
        DeclareConst(b1,BoolSort()),
        DeclareConst(c1,BoolSort()),
        DeclareConst(a2,BoolSort()),
        DeclareConst(b2,BoolSort()),
        DeclareConst(c2,BoolSort()),
  
        Assert(
          and(
            s1(smt1),
            s2(smt2),
            t(smt1,smt2)
          )
        ),
  
        CheckSat()
      ).map(z3.eval)
  
      z3.eval(GetModel()) match {
        case GetModelResponseSuccess(m) =>
          val res = comprehend(m)
          for {
            boolA1 <- res get a1
            boolB1 <- res get b1
            boolC1 <- res get c1
            boolA2 <- res get a2
            boolB2 <- res get b2
            boolC2 <- res get c2
          } yield ((
            B3(boolA1,boolB1,boolC1),
            B3(boolA2,boolB2,boolC2)
          ))
        case _ => None
      }
    }
  
    def comprehend(m: List[SExpr]): IMap[SSymbol,Boolean] = {
      val p: (IMap[SSymbol,Boolean],SExpr) => IMap[SSymbol,Boolean] = {
        (acc,i) => i match {
          case DefineFun(FunDef(id,_,_,v)) => v match {
            case True() => acc + (id -> true)
            case Not(True()) => acc + (id -> false)
            case False() => acc + (id -> false)
            case _ => acc
          }
          case _ => acc
        }
      }
      m.foldLeft[IMap[SSymbol,Boolean]](IMap.empty)(p)
    }
  
    def gammaHat(v: Vote3): SMT3 => Term = { (smt: SMT3) =>
      def yayFormula(smt: SMT3): Term = smt match {
        case SMT3(a,b,c) => Or(and(a,b), Or(and(b,c), and(c,a)))
      }
      v match {
        case Top => True()
        case Yay => yayFormula(smt)
        case Nay => not(yayFormula(smt))
        case Bot => False()
      }
    }
  
  
    implicit def represent(b: Boolean): Term => Term = { (t: Term) =>
      b match {
        case true => t
        case false => not(t)
      }
    }
  
    implicit def represent(b3: B3): SMT3 => Term = { (smt: SMT3) =>
      and(
        b3.bool1(smt.bool1),
        b3.bool2(smt.bool2),
        b3.bool3(smt.bool3)
      )
    }
  
    /** A single vote changes from Nay to Yay (note that this will fail if
      * all votes in the precondition are already Yay; this could maybe
      * be defined to also pass in that case?) */
    def voteYay(pre: SMT3, post: SMT3): Term = (pre,post) match {
      case (SMT3(a,b,c),SMT3(an,bn,cn)) =>
        Or(
          and(not(a),an,Equals(b,bn),Equals(c,cn)),
          and(Equals(a,an),not(b),bn,Equals(c,cn)),
          and(Equals(a,an),Equals(b,bn),not(c),cn)
        )
    }
  
    /** Opposite of voteYay */
    def voteNay(pre: SMT3, post: SMT3): Term = (pre,post) match {
      case (SMT3(a,b,c),SMT3(an,bn,cn)) =>
        Or(
          and(a,not(an),Equals(b,bn),Equals(c,cn)),
          and(Equals(a,an),b,not(bn),Equals(c,cn)),
          and(Equals(a,an),Equals(b,bn),c,not(cn))
        )
    }
  
  }
  
  /** The abstract form, which loses precision */
  sealed abstract class Vote3
  case object Top extends Vote3
  case object Yay extends Vote3
  case object Nay extends Vote3
  case object Bot extends Vote3
  
  object Vote3 {
    implicit val abstractVote3: Abstract[Vote3] = new Abstract[Vote3] {
  
      val bottom: Vote3 = Bot
      def isBottom(e: Vote3): Boolean = e == bottom
      def implies(e1: Vote3, e2: Vote3): Boolean = (e1,e2) match {
        case (Bot,_) => true
        case (_,Top) => true
        case _ => false
      }
      def join(e1: Vote3, e2: Vote3): Vote3 = (e1, e2) match {
        case _ if e1 == e2 => e1
        case (Bot, _) => e2
        case (_, Bot) => e1
        case _ => Top
      }
  
    }
  
    // def represent(b3: B3): Vote3 = b3 match {
    //   case B3(a,b,c) => Seq(a,b,c).filter((b: Boolean) => b).size match {
    //     case s if s >= 2 => Yay
    //     case _ => Nay
    //   }
    // }

    def represent(b3: B3): Vote3 = b3 match {
      case B3(true,true,true) => Yay
      case B3(true,true,_   ) => Yay
      case B3(_   ,true,true) => Yay
      case B3(true,_   ,true) => Yay
      case _ => Nay
    }
  
    def voteYay(vs: Vote3): Vote3 = vs match {
      case Yay => Yay
      case Nay => Top
      case v => v
    }
  
    def voteNay(vs: Vote3): Vote3 = vs match {
      case Nay => Nay
      case Yay => Top
      case v => v
    }
  
  }

}
