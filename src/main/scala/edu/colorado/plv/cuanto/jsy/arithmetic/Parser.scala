package edu.colorado.plv.cuanto.jsy
package arithmetic

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse into the arithmetic sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  /** ''opatom'' ::= ''float'' */
  abstract override def opatom: Parser[Expr] =
    positioned {
      floatingPointNumber ^^ (s => N(s.toDouble))
    } |
    super.opatom

  /** ''uop'' ::= '-' */
  abstract override def uop: Parser[Uop] =
    "-" ^^ { _ => Neg } |
    super.uop

  /** Precedence: { '+', '-' } < { '*', '/' }.
    *
    * ''bop'' ::= '+' | '-' | '*' | '/'
    */
  lazy val arithmeticBop: OpPrecedence = List(
      /* lowest */
      List("+" -> Plus, "-" -> Minus),
      List("*" -> Times, "/" -> Div)
      /* highest */
  )
}

/** The parser for just this arithmetic sub-language. */
object Parser extends UnitOpParser with ParserLike {
  override def start: Parser[Expr] = expr

  /** Parser for expressions ''expr'': [[Expr]].
    *
    * ''expr'' ::= ''binary''
    */
  override def expr: Parser[Expr] = binary

  /** Define precedence of left-associative binary operators. */
  override lazy val bop: OpPrecedence = arithmeticBop
}
