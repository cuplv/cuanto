package edu.colorado.plv.cuanto
package abstracting
package numerical

/** A domain representing an integer constrained by an upper and/or
  * lower bound
  *
  * Elements of the [[Interval]] domain should be constructed using
  * the [[Interval.btw `btw`]], [[Interval.lte `lte`]], and
  * [[Interval.gte `gte`]] operations defined in the companion object.
  *
  * @author octalsrc
  */
sealed abstract class Interval
case object Top extends Interval
case object Bot extends Interval

case class Gte(gte: Int) extends Interval
case class Lte(lte: Int) extends Interval
case class Btw(gte: Int, lte: Int) extends Interval

object Interval {
  import math.{min,max}

  private[this] type I = Interval

  private[this] val reduce: I => I = e => e match {
    case Btw(g,l) if g > l => Bot
    case e => e
  }

  /** Produce an abstract element representing [[Int]]s greater than or
    * equal to this [[Int]] */
  val gte: Int => Interval = l => Gte(l)

  /** Produce an abstract element representing [[Int]]s less than or
    * equal to this [[Int]] */
  val lte: Int => Interval = g => Lte(g)

  /** Produce an abstract element representing [[Int]]s within the
    * inclusive bounds of the arguments (greater than or equal to the
    * first `Int` and less than or equal to the second `int`) */
  val btw: (Int,Int) => Interval = (g,l) => reduce(Btw(g,l))

  object implicits {
    implicit val semilatticeInterval: Semilattice[Interval] = new Semilattice[I] {
      val bot: Interval = Bot
      def implies(e1: I,e2: I): Boolean = (e1,e2) match {
        case (Bot,_) => true
        case (_,Top) => true
        case (Gte(g1),Gte(g2)) if g1 >= g2 => true
        case (Lte(l1),Lte(l2)) if l1 <= l2 => true
        case (Btw(g1,_),Gte(g2)) if g1 >= g2 => true
        case (Btw(_,l1),Lte(l2)) if l1 <= l2 => true
        case (Btw(g1,l1),Btw(g2,l2)) if g1 <= g2 && l1 >= l2 => true
        case _ => false
      }
      def join(e1: I,e2: I): I = (e1,e2) match {
        case _ if e1 == e2 => e1

        case (Gte(g1),Gte(g2)) => gte(min(g1,g2))
        case (Lte(l1),Lte(l2)) => lte(max(l1,l2))
  
        case (Gte(g1),Lte(l2)) => btw(g1,l2)
        case (Lte(l2),Gte(g1)) => join(gte(g1),lte(l2))
  
        case (Btw(g1,l1),Gte(g2)) => gte(min(g1,g2))
        case (Gte(g2),Btw(g1,l1)) => join(btw(g1,l1),gte(g2))
  
        case (Btw(g1,l1),Lte(l2)) => lte(max(l1,l2))
        case (Lte(l2),Btw(g1,l1)) => join(btw(g1,l1),lte(l2))
  
        case (Btw(g1,l1),Btw(g2,l2)) => btw(min(g1,g2),max(l1,l2))
  
        case (Bot,_) => e2
        case (_,Bot) => e1
        case _ => Top
      }
    }

    implicit val abstractionInterval: Abstraction[Int,Interval] =
      new Abstraction[Int,Interval] {
        override def represent(c: Int): I = btw(c,c)
      }

  }

}
