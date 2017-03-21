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

  /** Values ''v'' with ''e'' ::= ''v''. */
  trait Val extends Expr

  /** Field access ''e'' ::= ''e,,1,,''[''e,,2,,'']. */
  case class Field(e1: Expr, e2: Expr) extends Expr

  /** Variables ''x'' with ''e'' ::= ''x''. */
  case class Var(name: String) extends Expr

  /** Assignments ''e'' ::= ''e,,l,,'' := ''e,,r,,''.*/
  case class Assign(l: LVal, r: Expr) extends Expr

  /** Expression sequencing ''e'' ::= ''e,,1,,'' ; ''e,,2,,''. */
  case class Seq(e1: Expr, e2: Expr) extends Expr

  /** Function abstractions ''e'' ::= fix ''f'' ''xs'' . ''e''. */
  case class Fix(f: Var, xs: Seq[Var], e: Expr) extends Expr

  /** Function applications ''e'' ::= ''f'' ''xs''. */
  case class App(f: Expr, xs: Seq[Expr]) extends Expr

  /** Variable bindings ''e'' ::= let ''x'' = ''e,,1,,'' in ''e,,2,,''. */
  case class Let(x: Var, e1: Expr, e2: Expr) extends Expr
}
