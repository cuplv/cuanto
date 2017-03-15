package edu.colorado.plv.cuanto.jsy
package arithmetic

/**
  * @author Bor-Yuh Evan Chang
  */
object implicits {

  /** Effectful: parses a [[String]] into an [[Expr]].
    *
    * @throws [[edu.colorado.plv.cuanto.parsing.SyntaxError]]
    */
  implicit def stringToExpr(s: String): Expr =
    Parser.parse(s).get

}
