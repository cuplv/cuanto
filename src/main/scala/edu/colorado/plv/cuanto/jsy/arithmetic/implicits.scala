package edu.colorado.plv.cuanto.jsy
package arithmetic

import edu.colorado.plv.cuanto.parsing

/**
  * @author Bor-Yuh Evan Chang
  */
object implicits {

  /** Effectful: parses a string into an [[edu.colorado.plv.cuanto.jsy.Expr]].
    *
    * @see [[Parser]].parse for the interface that returns a [[scala.util.Try]].
    * @throws parsing.SyntaxError when not syntactically valid.
    */
  @throws(classOf[parsing.SyntaxError])
  implicit def stringToExpr(s: String): Expr =
    Parser.parse(s).get

}
