package edu.colorado.plv.cuanto.abstracting

package bitvectors

import smtlib.theories._
import smtlib.parser.Terms._

case class B3(bool1: Boolean, bool2: Boolean, bool3: Boolean)

case class B3SMT(boo1: Term, bool2: Term, bool3: Term)

object B3SMT extends Abstractable[B3,B3SMT] {
  def represent(b3: B3): B3SMT = ???
}

sealed class Vote3
case object Top extends Vote3
case object Yay extends Vote3
case object Nay extends Vote3
case object Bot extends Vote3

object Vote3 extends Abstraction with Abstractable[B3,Vote3] {
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

  def represent(b3: B3): Vote3 = b3 match {
    case B3(a,b,c) => Seq(a,b,c).filter((b: Boolean) => b).size match {
      case s if s >= 2 => Yay
      case _ => Nay
    }
  }

  def voteYay(vs: Vote3): Vote3 = vs match {
    case Yay => Yay
    case Nay => Top
  }

  // def voteYayT(pre: B3, post: B3): Formula = (pre,post) match {
  //   case ((a1,a2,a3),(b1,b2,b3)) =>
  //     b1 && ((a2 && b2) || !(a2 || b2)) && ((a3 && b3) || !(a3 || b3))

  // }

  def voteNay(vs: Vote3): Vote3 = vs match {
    case Nay => Nay
    case Yay => Top
  }

  // def voteNayT(pre: B3, post: B3): Formula = (pre,post) match {
  //   case ((a1,a2,a3),(b1,b2,b3)) =>
  //     !b1 && ((a2 && b2) || !(a2 || b2)) && ((a3 && b3) || !(a3 || b3))
  // }
}
