package edu.colorado.plv.cuanto.jsy.arithmetic

import scala.util.parsing.input.Positional

/** Define an arithmetic sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
object ast {
  /** Expressions ''e''. */
  trait Expr extends Positional

  /* Literals and Values */

  /** Numbers ''v'' ::= ''n''. */
  case class N(n: Double) extends Expr

  /* Unary and Binary Operators */

  /** Unary expressions ''e'' ::= ''uop'' ''e,,1,,''. */
  case class Unary(op: Uop, e1: Expr) extends Expr

  /** Binary expressions ''e'' ::= ''e,,1,,'' ''bop'' ''e,,2,,''. */
  case class Binary(op: Bop, e1: Expr, e2: Expr) extends Expr

  /** Unary operators ''uop''. */
  sealed trait Uop

  /** Neg ''uop'' ::= `-`. */
  case object Neg extends Uop /* - */

  /** Binary operators ''bop''. */
  sealed trait Bop

  /** Plus ''bop'' ::= `+`. */
  case object Plus extends Bop /* + */
  /** Minus ''bop'' ::= `-`. */
  case object Minus extends Bop /* - */
  /** Times ''bop'' ::= `*`. */
  case object Times extends Bop /* * */
  /** Div ''bop'' ::= `/`. */
  case object Div extends Bop /* / */

  /** Define values. */
  def isValue(e: Expr): Boolean = e match {
    case N(_) => true
    case _ => false
  }

  object Value {
    def unapply(e: Expr): Option[Expr] = e match {
      case N(_) => Some(e)
      case _ => None
    }
  }

  /** Pretty-print values.
    *
    * We do not override the toString method so that the abstract syntax
    * can be printed as is.
    */
  def prettyValue(v: Expr): String = {
    require(isValue(v))
    v match {
      case N(n) => prettyDouble(n)
    }
  }

  /** Pretty-print doubles (as in JavaScript). */
  def prettyDouble(n: Double): String =
    if (n.isWhole) "%.0f" format n else n.toString
}
