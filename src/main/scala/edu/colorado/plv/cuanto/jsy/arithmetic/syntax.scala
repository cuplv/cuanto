package edu.colorado.plv.cuanto.jsy
package arithmetic

/** Define basic syntactic operations.
  *
  * @author Bor-Yuh Evan Chang
  */
object syntax {
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
