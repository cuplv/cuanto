package edu.colorado.plv.cuanto.jsy

/** Define a sub-language of numerical constraints.
  *
  * Combines [[arithmetic]] and [[boolean]].
  *
  * @author Bor-Yuh Evan Chang
  */
package numerical {

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

}
