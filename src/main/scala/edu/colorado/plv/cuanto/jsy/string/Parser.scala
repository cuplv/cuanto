package edu.colorado.plv.cuanto.jsy
package string

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse into the string sub-language.
  *
  * Relies on [[edu.colorado.plv.cuanto.jsy.common.OpParserLike]] to parse unary and binary expressions.
  *
  * @author Kyle Headley
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  abstract override def opAtom: Parser[Expr] =
    positioned {
      stringLiteral ^^ { s => S(s.substring(1, s.length - 1)) }
    } |
    super.opAtom

  lazy val stringBop: OpPrecedence = List(
      /* lowest */
      List("+" -> Concat)
      /* highest */
  )
}

/** The parser for just this string sub-language.
  *
  * @see [[ParserLike]]
  * @author Kyle Headley
  */
object Parser extends UnitOpParser with ParserLike {
  override def start: Parser[Expr] = expr
  override def expr: Parser[Expr] = binary
  override lazy val bop: OpPrecedence = stringBop
}
