package edu.colorado.plv.cuanto.abstracting

package bitvectors

import smtlib.theories.Core._
import smtlib.theories.Constructors._
import smtlib.parser.Terms._

import symbolic._

/** The concrete form, a tuple of three booleans */
case class B3(bool1: Boolean, bool2: Boolean, bool3: Boolean)

object B3 {
  val postHatUpInst =
    (t: Term => Term, v: Vote3) => postHatUp(modelB3 _,b3ToVote3 _,vote3GammaHat _,t,not,v)

  def b3ToVote3(b: B3): Vote3 = ???
  def vote3GammaHat(v: Vote3): SMT3 => Term = ???

  implicit val abstractVote3: Abstract[Vote3] = ???

  def modelB3(
    s1: (SMT3 => Term),
    t: (Term => Term),
    s2: (SMT3 => Term)
  ): Option[(B3,B3)] = ???

}

/** The symbolic form, capable of fully representing a B3 as an SMT
  * script */
case class SMT3(bool1: Term, bool2: Term, bool3: Term)

object SMT3 {



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

  implicit def represent(v: Vote3): SMT3 => Term = { (smt: SMT3) =>

    def yayFormula(smt: SMT3): Term = smt match {
      case SMT3(a,b,c) => or(and(a,b), and(b,c), and(c,a))
    }

    v match {
      case Top => True()
      case Yay => yayFormula(smt)
      case Nay => not(yayFormula(smt))
      case Bot => False()
    }
  }

  /** A single vote changes from Nay to Yay (note that this will fail if
    * all votes in the precondition are already Yay; this could maybe
    * be defined to also pass in that case?) */
  def voteYay(pre: SMT3, post: SMT3): Term = (pre,post) match {
    case (SMT3(a,b,c),SMT3(an,bn,cn)) =>
      or(
        and(not(a),an),
        and(not(b),bn),
        and(not(c),cn)
      )
  }

  /** Opposite of voteYay */
  def voteNay(pre: SMT3, post: SMT3): Term = (pre,post) match {
    case (SMT3(a,b,c),SMT3(an,bn,cn)) =>
      or(
        and(a,not(an)),
        and(b,not(bn)),
        and(c,not(cn))
      )
  }

}

/** The abstract form, which loses precision */
sealed abstract class Vote3
case object Top extends Vote3
case object Yay extends Vote3
case object Nay extends Vote3
case object Bot extends Vote3

object Vote3 extends Abstraction {
  type A = Vote3

  val bottom: Vote3 = Bot

  def isBottom(e: Vote3): Boolean = e match {
    case Bot => true
    case _ => false
  }

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

  implicit def represent(b3: B3): Vote3 = b3 match {
    case B3(a,b,c) => Seq(a,b,c).filter((b: Boolean) => b).size match {
      case s if s >= 2 => Yay
      case _ => Nay
    }
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
