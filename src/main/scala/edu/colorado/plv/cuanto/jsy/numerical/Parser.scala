package edu.colorado.plv.cuanto.jsy
package numerical

import edu.colorado.plv.cuanto.jsy.common.UnitOpParser

/** Extend with the numerical sub-language.
  *
  * Mixes the [[arithmetic.ParserLike]] sub-language with the
  * [[boolean.ParserLike]] sub-language with comparison operators.
  *
  * The numerical sub-language simply adds comparison operators:
  * `===`, `!==`, `<=`, `<`, `>=`, and `>`.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends boolean.ParserLike with arithmetic.ParserLike {
  lazy val comparisonBop: OpPrecedence = List(
    /* lowest */
    List("===" -> Eq, "!==" -> Ne),
    List("<=" -> Le, "<" -> Lt, ">=" -> Ge, ">" -> Gt)
    /* highest */
  )

  override lazy val bop: OpPrecedence =
    /* lowest */
    booleanBop ++ (comparisonBop ++ arithmeticBop)
    /* highest */

  override def start: Parser[Expr] = expr
  override def expr: Parser[Expr] = iteop
  override def itesub: Parser[Expr] = iteop
}

/** The parser for just this numerical sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
object Parser extends UnitOpParser with ParserLike



