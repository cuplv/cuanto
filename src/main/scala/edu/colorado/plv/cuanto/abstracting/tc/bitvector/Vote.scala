package edu.colorado.plv.cuanto
package abstracting.tc
package bitvector

/** A domain representing a majority ([[Yay]]) or minority ([[Nay]])
  * among a set of boolean values
  *
  * @author octalsrc
  */
sealed abstract class Vote
case object Top extends Vote
case object Bot extends Vote

case object Yay extends Vote
case object Nay extends Vote

object Vote {
  private[this] type V = Vote

  object implicits {

    implicit val semilatticeVote: Semilattice[V] = new Semilattice[V] {
      val bot: V = Bot
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
    }

    implicit val abstractionVote: Abstraction[Boolean,V] =
      new Abstraction[Boolean,V] {
        override def represent(c: Boolean) = c match {
          case true => Yay
          case false => Nay
        }
      }

    implicit val abstractionVote3: Abstraction[(Boolean,Boolean,Boolean),V] =
      new Abstraction[(Boolean,Boolean,Boolean),V] {
        override def represent(c: (Boolean,Boolean,Boolean)) = c match {
          case (true,true,_) => Yay
          case (_,true,true) => Yay
          case (true,_,true) => Yay
          case _ => Nay
        }
      }

    implicit val abstractionVoteList: Abstraction[List[Boolean],V] =
      new Abstraction[List[Boolean],V] {
        override def represent(c: List[Boolean]) = {
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
