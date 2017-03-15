package edu.colorado.plv.cuanto.jsy

/** Define an boolean sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
package object boolean {

  /* Literals and Values */

  /** Booleans ''e'' ::= ''b''. */
  case class B(b: Boolean) extends Expr

  /* Operators */

  /** Unary operators ''uop''. */

  /** Not ''uop'' ::= `!`. */
  case object Not extends Uop

  /** Binary operators ''bop''. */

  /** And ''uop'' ::= `&&`. */
  case object And extends Bop
  /** Or ''uop'' ::= `||`. */
  case object Or extends Bop

  /** Equal ''bop'' ::= `==`. */
  case object Eq extends Bop
  /** Not equal ''bop'' ::= `!=`. */
  case object Ne extends Bop
  /** Less than or equal ''bop'' ::= `<=`. */
  case object Le extends Bop
  /** Less than ''bop'' ::= `<`. */
  case object Lt extends Bop
  /** Greater than or equal ''bop'' ::= `>=`. */
  case object Ge extends Bop
  /** Greater than ''bop'' ::= `>`. */
  case object Gt extends Bop

  /** Elimination: if-then-else ''e'' ::= ''e,,1,,'' ? ''e,,2,,'' : ''e,,3,,''. */
  case class If(e1: Expr, e2: Expr, e3: Expr) extends Expr

}
