package edu.colorado.plv.cuanto.jsy.arithmetic

import edu.colorado.plv.cuanto.jsy.arithmetic.ast.Expr
import edu.colorado.plv.cuanto.parsing.ParserLike

/**
  * @author Bor-Yuh Evan Chang
  */
object implicits {

  /** Effectful: parses a [[String]] into an [[Expr]].
    *
    * @throws ParserLike#SyntaxError
    */
  implicit def stringToExpr(s: String): Expr =
    Parser.parse(s).get

}
