package edu.colorado.plv.cuanto.jsy
package numerical

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse into the numerical sub-language.
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

/** The parser for just this numerical sub-language. */
object Parser extends UnitOpParser with ParserLike with boolean.ParserLike with arithmetic.ParserLike {
  override def start: Parser[Expr] = binary

  /** Define precedence of left-associative binary operators. */
  override lazy val bop: OpPrecedence =
    /* lowest */
    booleanBop ++ (comparisonBop ++ arithmeticBop)
    /* highest */
}
