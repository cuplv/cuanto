package edu.colorado.plv.cuanto.jsy.arithmetic

import scala.util.parsing.input.Positional

/** Define an arithmetic sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
object ast {
  /** Expressions ''e''. */
  sealed trait Expr extends Positional

  /* Literals and Values */

  /** Numbers ''n''. */
  case class N(n: Double) extends Expr

  /* Unary and Binary Operators */

  /** Unary expressions ''e'' ::= ''op'' ''e,,1,,''. */
  case class Unary(op: Op, e1: Expr) extends Expr
  /** Binary expressions ''e'' ::= ''e,,1,,'' ''op'' ''e,,2,,''. */
  case class Binary(op: Op, e1: Expr, e2: Expr) extends Expr

  /** Operators ''op''. */
  sealed trait Op

  /** Plus ''op'' ::= `+`. */
  case object Plus extends Op /* + */
  /** Minus ''op'' ::= `-`. */
  case object Minus extends Op /* - */
  /** Times ''op'' ::= `*`. */
  case object Times extends Op /* * */
  /** Div ''op'' ::= `/`. */
  case object Div extends Op /* / */

  /** Define well-formed expressions. */
  def isWellFormed(e: Expr): Boolean = e match {
    case N(_) => true
    case Unary(Minus, e1) => isWellFormed(e1)
    case Binary(_, e1, e2) => isWellFormed(e1) && isWellFormed(e2)
    case _ => false
  }

  /** Define values. */
  def isValue(e: Expr): Boolean = e match {
    case N(_) => true
    case _ => false
  }

  /** Pretty-print values.
    *
    * We do not override the toString method so that the abstract syntax
    * can be printed as is.
    */
  def prettyValue(v: Expr): String = {
    require(isValue(v))
    (v: @unchecked) match {
      case N(n) => prettyDouble(n)
    }
  }

  /** Pretty-print doubles (as in JavaScript). */
  def prettyDouble(n: Double): String =
    if (n.isWhole) "%.0f" format n else n.toString
}
