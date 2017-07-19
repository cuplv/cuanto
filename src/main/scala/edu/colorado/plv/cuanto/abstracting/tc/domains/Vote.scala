package edu.colorado.plv.cuanto
package abstracting.tc
package domains

/** A domain representing a majority ([[yay `yay`]]) or minority ([[nay `nay`]])
  * among a set of boolean values
  *
  * @author Nicholas V. Lewchenko
  */
package object Vote {

  sealed abstract class Vote

  private[domains] case object Top extends Vote
  private[domains] case object Bot extends Vote

  private[domains] case object Yay extends Vote
  private[domains] case object Nay extends Vote

  private[this] type V = Vote

  /** Majority vote (meaning a majority of the booleans were `true`) */
  val yay: Vote = Yay

  /** Minority vote (majority were `false`) */
  val nay: Vote = Nay

  object instances {

    implicit val latticeVote: Lattice[Vote] = new Lattice[V] {
      val bot: V = Bot
      val top: V = Top
      def implies(e1: V,e2: V): Boolean = (e1,e2) match {
        case (Bot,_) => true
        case (_,Top) => true
        case _ if e1 == e2 => true
        case _ => false
      }
      def join(e1: V,e2: V): V = (e1,e2) match {
        case (Bot,_) => e2
        case (_,Bot) => e1
        case _ if e1 == e2 => e1
        case _ => Top
      }
      def meet(e1: V,e2: V): V = (e1,e2) match {
        case (Top,_) => e2
        case (_,Top) => e1
        case _ if e1 == e2 => e1
        case _ => Bot
      }
    }

    implicit val abstractionVote: Abstraction[Boolean,Vote] =
      new Abstraction[Boolean,V] {
        override def beta(c: Boolean) = c match {
          case true => Yay
          case false => Nay
        }
      }

    implicit val abstractionVote3: Abstraction[(Boolean,Boolean,Boolean),Vote] =
      new Abstraction[(Boolean,Boolean,Boolean),V] {
        override def beta(c: (Boolean,Boolean,Boolean)) = c match {
          case (true,true,_) => Yay
          case (_,true,true) => Yay
          case (true,_,true) => Yay
          case _ => Nay
        }
      }

    implicit val abstractionVoteList: Abstraction[List[Boolean],Vote] =
      new Abstraction[List[Boolean],V] {
        override def beta(c: List[Boolean]) = {
          val test: (Boolean,Int) => Int = (a,b) => a match {
            case true => b + 1
            case _ => b
          }
          val l: Int = c.size
          val t: Int = c.foldRight(0)(test)
  
          () match {
            case _ if t * 2 > l => Yay
            case _ if t * 2 < l => Nay
            case _ => Top
          }
        }
      }
  
  }
}
