package edu.colorado.plv.cuanto.abstracting

import edu.colorado.plv.cuanto.jsy.arithmetic


/** Sign abstraction over Doubles
  *
  * @author Benno Stein
  * @author Kyle Headley
  */

sealed class Sign
case object Pos extends Sign
case object Neg  extends Sign
case object Bot  extends Sign
case object Top  extends Sign
case object Zero extends Sign

object Sign extends Abstraction with arithmetic.Evalable[Sign] with Abstractable[Sign] {
  type A = Sign
  val bottom: Sign = Bot

  def implies(e1: Sign, e2: Sign): Boolean = (e1, e2) match {
    case (Bot, _) => true
    case (_, Top) => true
    case _ => false
  }

  def join(e1: Sign, e2: Sign): Sign = (e1, e2) match {
    case _ if e1 == e2 => e1
    case (Bot, _) => e2
    case (_, Bot) => e1
    case _ => Top
  }

  def represent(v: Any): Sign = v match {
    case 0 => Zero
    case x:Double if x > 0 => Pos
    case x:Double if x < 0 => Neg
    case _ => throw new UnsupportedOperationException("Can't represent a non-numerical value as a double")
  }

  def divide(v1: Sign, v2: Sign): Sign = (v1, v2) match {
    case (Bot, _) => Bot
    case (_, Bot) => Bot
    case (_, Zero) => Bot
    case (Top, _) => Top
    case (_, Top) => Top
    case (Zero, _) => Zero
    case _ if v1 == v2 => Pos
    case _ /*v1!=v2*/ => Neg
  }

  def negate(v: Sign): Sign = v match {
    case Pos => Neg
    case Neg => Pos
    case _ => v
  }

  def plus(v1: Sign, v2: Sign): Sign = (v1, v2) match {
    case (Bot, _) => Bot
    case (_, Bot) => Bot
    case (Top, _) => Top
    case (_, Top) => Top
    case (Zero, x) => x
    case (x, Zero) => x
    case _ if v1 == v2 => v1
    case _ /*v1!=v2*/ => Top
  }

  def times(v1: Sign, v2: Sign): Sign = (v1, v2) match {
    case (Bot, _) => Bot
    case (_, Bot) => Bot
    case (Top, _) => Top
    case (_, Top) => Top
    case (_, Zero) => Zero
    case (Zero, _) => Zero
    case _ if v1 == v2 => Pos
    case _ /*v1!=v2*/ => Neg
  }
}