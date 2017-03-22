package edu.colorado.plv.cuanto.jsy
package boolean

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse into the boolean sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  override def start: Parser[Expr] = expr

  /** ''atom'' ::= ''float'' */
  abstract override def opatom: Parser[Expr] =
    positioned {
      "true" ^^^ B(true) |
      "false" ^^^ B(false)
    } |
    super.opatom

  /** ''uop'' ::= '-' */
  abstract override def uop: Parser[Uop] =
    "!" ^^ { _ => Not } |
    super.uop

  /** Precedence: { '||' } < { '&&' }.
    *
    * ''bop'' ::= '\\' | '&&'
    */
  lazy val booleanBop: OpPrecedence = List(
    /* lowest */
    List("||" -> Or),
    List("&&" -> And)
    /* highest */
  )
}

/** The parser for just this boolean sub-language */
object Parser extends UnitOpParser with ParserLike {
  override def start: Parser[Expr] = expr

  /** Parser for expressions ''expr'': [[Expr]].
    *
    * ''expr'' ::= ''binary''
    */
  override def expr: Parser[Expr] = binary

  /** Define precedence of left-associative binary operators. */
  override lazy val bop: OpPrecedence = booleanBop
}
