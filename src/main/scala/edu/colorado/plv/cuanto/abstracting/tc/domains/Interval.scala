package edu.colorado.plv.cuanto
package abstracting.tc
package domains

import smtlib.Interpreter
import smtlib.parser.Commands.{Command, DeclareConst}
import smtlib.parser.Terms._
import smtlib.theories.Ints._

import symbolic._

/** A domain representing an integer constrained by an upper and/or
  * lower bound
  *
  * Elements of the [[Interval]] domain should be constructed using
  * the [[btw `btw`]], [[lte `lte`]], and [[gte `gte`]] operations
  * defined in the companion object.
  *
  * @author Nicholas V. Lewchenko
  */
package object interval {
  import math.{min,max}

  sealed abstract class Interval

  private[this] case object Top extends Interval
  private[this] case object Bot extends Interval

  private[this] case class Gte(gte: Int) extends Interval
  private[this] case class Lte(lte: Int) extends Interval
  private[this] case class Btw(gte: Int, lte: Int) extends Interval

  private[this] type I = Interval


  /** Produce an abstract element representing [[Int]]s greater than or
    * equal to this [[Int]] */
  val gte: Int => Interval = l => Gte(l)

  /** Produce an abstract element representing [[Int]]s less than or
    * equal to this [[Int]] */
  val lte: Int => Interval = g => Lte(g)

  /** Produce an abstract element representing [[Int]]s within the
    * inclusive bounds of the arguments (greater than or equal to the
    * first `Int` and less than or equal to the second `int`) */
  val btw: (Int,Int) => Interval = (g,l) => {
    val reduce: I => I = e => e match {
      case Btw(g,l) if g > l => Bot
      case e => e
    }
    reduce(Btw(g,l))
  }

  case class IntSMT(int1: SSymbol)

  object instances {
    implicit val intSMTSymbol: Symbol[IntSMT] = new Symbol[IntSMT] {
      override def draw(name: String): (IntSMT,Traversable[Command]) = {
        val s: SSymbol = SSymbol(name)

        (IntSMT(s),Seq(DeclareConst(s,IntSort())))
      }
    }

    implicit val intSMTVal: SMTVal[Int] = new SMTVal[Int] {
      override def interpret(value: Term): Option[Int] = value match {
        case NumeralLit(v) => Some(v.intValue())
        case _ => None
      }
    }

    implicit val intModel: Model[Int] {type Schema = IntSMT} = new Model[Int] {
      override type Schema = IntSMT
      override def getModel(name: String, i: Interpreter): Option[Int] = {
        for {
          intResults <- getModelMap(i)
          int <- intResults get SSymbol(name)
        } yield int
      }
    }

    implicit val intervalSym: SymAbstract[Interval,IntSMT] =
      new SymAbstract[Interval,IntSMT] {
        override def gammaHat(a: Interval): Constraint[IntSMT] = ???
      }
      
    implicit val latticeInterval: Lattice[Interval] = new Lattice[I] {
      val bot: Interval = Bot
      val top: Interval = Top
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

        case (Gte(g1),Lte(l2)) => top
        case (Lte(l2),Gte(g1)) => join(gte(g1),lte(l2))

        case (Btw(g1,l1),Gte(g2)) => gte(min(g1,g2))
        case (Gte(g2),Btw(g1,l1)) => join(btw(g1,l1),gte(g2))

        case (Btw(g1,l1),Lte(l2)) => lte(max(l1,l2))
        case (Lte(l2),Btw(g1,l1)) => join(btw(g1,l1),lte(l2))

        case (Btw(g1,l1),Btw(g2,l2)) => btw(min(g1,g2),max(l1,l2))

        case (Bot,_) => e2
        case (_,Bot) => e1
        case _ => top
      }
      def meet(e1: I,e2: I): I = (e1,e2) match {
        case _ if e1 == e2 => e1

        case (Gte(g1),Gte(g2)) => gte(max(g1,g2))
        case (Lte(l1),Lte(l2)) => lte(min(l1,l2))

        case (Gte(g1),Lte(l2)) => btw(g1,l2)
        case (Lte(l2),Gte(g1)) => join(gte(g1),lte(l2))

        case (Btw(g1,l1),Gte(g2)) => btw(max(g1,g2),l1)
        case (Gte(g2),Btw(g1,l1)) => join(btw(g1,l1),gte(g2))

        case (Btw(g1,l1),Lte(l2)) => btw(g1,min(l1,l2))
        case (Lte(l2),Btw(g1,l1)) => join(btw(g1,l1),lte(l2))

        case (Btw(g1,l1),Btw(g2,l2)) => btw(max(g1,g2),min(l1,l2))

        case (Top,_) => e2
        case (_,Top) => e1
        case _ => bot
      }
    }

    implicit val abstractionInterval: Abstraction[Int,Interval] =
      new Abstraction[Int,Interval] {
        override def beta(c: Int): I = btw(c,c)
      }

  }

}
