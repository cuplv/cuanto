package edu.colorado.plv.cuanto.jsy

/** Define an string sub-language.
  *
  * @author Kyle Headley
  */
package binding {

  /* Literals and values. */

  /** Variables ''e'' ::= ''s''. */
  case class Var(s: String) extends Expr

  /** Variable binding: ''e'' ::= let ''v'' = ''e,,1,,'' in ''e,,2,,''. */
  case class Bind(v: Expr, e1: Expr, e2: Expr) extends Expr

}

