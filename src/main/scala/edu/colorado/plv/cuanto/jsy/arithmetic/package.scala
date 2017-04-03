package edu.colorado.plv.cuanto.jsy

/** Define an arithmetic sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
package arithmetic {

  /* Literals and values. */

  /** Numbers ''e'' ::= ''n''. */
  case class N(n: Double) extends Val

  /* Unary and binary operators. */

  /** Neg ''uop'' ::= `-`. */
  case object Neg extends Uop

  /** Plus ''bop'' ::= `+`. */
  case object Plus extends Bop

  /** Minus ''bop'' ::= `-`. */
  case object Minus extends Bop

  /** Times ''bop'' ::= `*`. */
  case object Times extends Bop

  /** Div ''bop'' ::= `/`. */
  case object Div extends Bop

}

package object arithmetic {

  /** Define values. */
  def isValue(e: Expr): Boolean = e match {
    case N(_) => true
    case _ => false
  }

  /** Define extractor for values. */
  object Value {
    def unapply(e: Expr): Option[Expr] = e match {
      case N(_) => Some(e)
      case _ => None
    }
  }

}
