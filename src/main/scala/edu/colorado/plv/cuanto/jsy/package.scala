package edu.colorado.plv.cuanto

import scala.util.parsing.input.Positional

/**
  * @author Bor-Yuh Evan Chang
  */
package jsy {

  /** Expressions ''e''. */
  trait Expr extends Positional

  /** Unary expressions ''e'' ::= ''uop'' ''e,,1,,''. */
  case class Unary(op: Uop, e1: Expr) extends Expr

  /** Binary expressions ''e'' ::= ''e,,1,,'' ''bop'' ''e,,2,,''. */
  case class Binary(op: Bop, e1: Expr, e2: Expr) extends Expr

  /** Unary operators ''uop''. */
  trait Uop

  /** Binary operators ''bop''. */
  trait Bop

}
