package edu.colorado.plv.cuanto.jsy
package boolean

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike}

/** Parse into the boolean sub-language.
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
      "true" ^^^ B(true) |
      "false" ^^^ B(false)
    }

  /** ''uop'' ::= '-' */
  def uop: Parser[Uop] =
    "!" ^^ { _ => Not }

  /** Define precedence of left-associative binary operators.
    *
    * ''bop'' ::= '+' | '-' | '*' | '/'
    */
  lazy val bop: OpPrecedence = List(
    /* lowest */
    List("||" -> Or),
    List("&&" -> And)
    /* highest */
  )
}
