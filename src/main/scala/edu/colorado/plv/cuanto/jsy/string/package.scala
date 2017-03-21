package edu.colorado.plv.cuanto.jsy

/** Define an string sub-language.
  *
  * @author Kyle Headley
  */
package string {

  /* Literals and values. */

  /** Strings ''e'' ::= ''"s"''. */
  case class S(s: String) extends Expr

  /* Binary operators. */

  /** Plus ''bop'' ::= `+`. */
  case object Concat extends Bop

}

package object string {

  /** Define string. */
  def isValue(e: Expr): Boolean = e match {
    case S(_) => true
    case _ => false
  }

  /** Define extractor for strings. */
  object Value {
    def unapply(e: Expr): Option[Expr] = e match {
      case S(_) => Some(e)
      case _ => None
    }
  }

}
