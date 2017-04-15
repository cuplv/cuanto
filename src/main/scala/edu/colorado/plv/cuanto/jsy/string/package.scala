package edu.colorado.plv.cuanto.jsy

package string {

  /** Strings ''e'' ::= `"` ''s'' `"`. */
  case class S(s: String) extends Expr

}

/** Define a string sub-language.
  *
  * @author Kyle Headley
  * @author Bor-Yuh Evan Chang
  */
package object string {

  /** The string concatenation operator is syntactically overloaded as
    * [[edu.colorado.plv.cuanto.jsy.arithmetic.Plus]].
    */
  val Concat: Bop = arithmetic.Plus

}
