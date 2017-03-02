package edu.colorado.plv.cuanto.jsy.arithmetic

import edu.colorado.plv.cuanto.jsy.arithmetic.ast.Expr
import edu.colorado.plv.cuanto.parsing.ParserLike

import scala.util.parsing.combinator.JavaTokenParsers
import scala.util.parsing.input.Reader

/** Parse into the arithmetic sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
object Parser extends JavaTokenParsers with ParserLike[Expr] {
  import edu.colorado.plv.cuanto.jsy.arithmetic.ast._
  override def scan(in: Reader[Char]): Input = in
  override def start: Parser[Expr] = expr

  /* Specify the syntax. */

  def expr: Parser[Expr] = atom

  def atom: Parser[Expr] =
    positioned(
     floatingPointNumber ^^ (s => N(s.toDouble))
    ) |
    "(" ~> expr <~ ")" |
    failure("expected an atom")

}
