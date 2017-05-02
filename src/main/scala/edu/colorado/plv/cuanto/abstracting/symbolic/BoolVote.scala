package edu.colorado.plv.cuanto.abstracting

package symbolic

sealed class BoolVote
case object Top extends BoolVote
case object Yay extends BoolVote
case object Nay extends BoolVote
case object Bot extends BoolVote

object BoolVote extends Abstraction
    with Abstractable[(Boolean,Boolean,Boolean),BoolVote] {
  type A = BoolVote
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

  def vote(b: Boolean)(vs: BoolVote): BoolVote = (b,vs) match {
    case (true,Yay) => Yay
    case (true,Nay) => Top
    case (false,Nay) => Nay
    case (false,Yay) => Top
  }
}
