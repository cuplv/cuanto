package edu.colorado.plv.cuanto.jsy
package arithmetic

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike}

/** Parse into the arithmetic sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
object Parser extends OpParserLike with JsyParserLike {
  override def start: Parser[Expr] = expr

  /** Parser for expressions ''expr'': [[Expr]].
    *
    * ''expr'' ::=
    *
    */
  def expr: Parser[Expr] = binary

  /** ''atom'' ::= ''float'' */
  def opatom: Parser[Expr] =
    positioned {
      floatingPointNumber ^^ (s => N(s.toDouble))
    }

  /** ''uop'' ::= '-' */
  def uop: Parser[Uop] =
    "-" ^^ { _ => Neg }

  /** Define precedence of left-associative binary operators.
    *
    * ''bop'' ::= '+' | '-' | '*' | '/'
    */
  lazy val bop: OpPrecedence = List(
    /* lowest */
    List("+" -> Plus, "-" -> Minus),
    List("*" -> Times, "/" -> Div)
    /* highest */
  )
}
