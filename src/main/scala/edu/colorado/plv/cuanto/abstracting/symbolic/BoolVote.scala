package edu.colorado.plv.cuanto.abstracting

package symbolic

import cafesat.api._
import cafesat.api.FormulaBuilder._
import cafesat.api.Formulas._



sealed class BoolVote
case object Top extends BoolVote
case object Yay extends BoolVote
case object Nay extends BoolVote
case object Bot extends BoolVote

object BoolVote extends Abstraction
    with Abstractable[(Boolean,Boolean,Boolean),BoolVote] {
  type A = BoolVote
  type B3 = (PropVar,PropVar,PropVar)
  val bottom: BoolVote = Bot

  def isBottom(e: BoolVote): Boolean = e match {
    case Bot => true
    case _ => false
  }

  def implies(e1: BoolVote, e2: BoolVote): Boolean = (e1,e2) match {
    case (Bot,_) => true
    case (_,Top) => true
    case _ => false
  }

  def join(e1: BoolVote, e2: BoolVote): BoolVote = (e1, e2) match {
    case _ if e1 == e2 => e1
    case (Bot, _) => e2
    case (_, Bot) => e1
    case _ => Top
  }

  def represent(bs: (Boolean,Boolean,Boolean)): BoolVote = bs match {
    case (a,b,c) => Seq(a,b,c).filter((b: Boolean) => b).size match {
      case s if s >= 2 => Yay
      case _ => Nay
    }
  }

  def voteYay(vs: BoolVote): BoolVote = vs match {
    case Yay => Yay
    case Nay => Top
  }

  def voteYayT(pre: B3, post: B3): Formula = (pre,post) match {
    case ((a1,a2,a3),(b1,b2,b3)) =>
      b1 && ((a2 && b2) || !(a2 || b2)) && ((a3 && b3) || !(a3 || b3))

  }

  def voteNay(vs: BoolVote): BoolVote = vs match {
    case Nay => Nay
    case Yay => Top
  }

  def voteNayT(pre: B3, post: B3): Formula = (pre,post) match {
    case ((a1,a2,a3),(b1,b2,b3)) =>
      !b1 && ((a2 && b2) || !(a2 || b2)) && ((a3 && b3) || !(a3 || b3))
  }
}
