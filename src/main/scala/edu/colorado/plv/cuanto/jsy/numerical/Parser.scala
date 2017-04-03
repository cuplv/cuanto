package edu.colorado.plv.cuanto.jsy
package numerical

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Extend with the numerical sub-language.
  *
  * The numerical sub-language simply adds comparison operators:
  * `===`, `!==`, `<=`, `<`, `>=`, and `>`.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  lazy val comparisonBop: OpPrecedence = List(
    /* lowest */
    List("===" -> Eq, "!==" -> Ne),
    List("<=" -> Le, "<" -> Lt, ">=" -> Ge, ">" -> Gt)
    /* highest */
  )
}

/** The parser for just this numerical sub-language.
  *
  * Mixes the [[arithmetic.ParserLike]] sub-language with the
  * [[boolean.ParserLike]] sub-language with comparison operators.
  *
  * @author Bor-Yuh Evan Chang
  */
object Parser extends UnitOpParser with ParserLike with boolean.ParserLike with arithmetic.ParserLike {
  override def start: Parser[Expr] = expr
  override def expr: Parser[Expr] = iteop
  override def itesub: Parser[Expr] = expr
  override lazy val bop: OpPrecedence =
    /* lowest */
    booleanBop ++ (comparisonBop ++ arithmeticBop)
    /* highest */
}
