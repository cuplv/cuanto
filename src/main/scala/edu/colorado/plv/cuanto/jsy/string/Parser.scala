package edu.colorado.plv.cuanto.jsy
package string

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse into the string sub-language.
  *
  * @author Kyle Headley
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  /** ''opatom'' ::= ''"string"'' */
  abstract override def opatom: Parser[Expr] =
    positioned {
      stringLiteral ^^ (s => S(s.substring(1, s.length()-1)))
    } |
    super.opatom

  /** Precedence: { '+' }
    *
    * ''bop'' ::= '+'
    */
  lazy val stringBop: OpPrecedence = List(
      /* lowest */
      List("+" -> Concat)
      /* highest */
  )
}

/** The parser for just this string sub-language. */
object Parser extends UnitOpParser with ParserLike {
  override def start: Parser[Expr] = expr

  /** Parser for expressions ''expr'': [[Expr]].
    *
    * ''expr'' ::= ''binary''
    */
  override def expr: Parser[Expr] = binary

  /** Define precedence of left-associative binary operators. */
  override lazy val bop: OpPrecedence = stringBop
}
