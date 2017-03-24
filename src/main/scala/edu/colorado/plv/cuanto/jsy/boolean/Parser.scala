package edu.colorado.plv.cuanto.jsy
package boolean

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse into the boolean sub-language.
  *
  * Relies on [[edu.colorado.plv.cuanto.jsy.common.OpParserLike]] to parse unary and binary expressions.
  *
  * The atoms are `true` and `false`
  *
  * $booleanOpatom
  *
  * The unary and binary operators are negation, or, and and:
  *
  * $booleanUop
  *
  * $booleanBop
  *
  * @define booleanOpatom ''opatom'' ::= `true` | `false`
  * @define booleanUop '''uop'' ::= `!``
  * @define booleanBop ''bop'' ::= `||` | `&&`
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  override def start: Parser[Expr] = expr

  /** $booleanOpatom */
  abstract override def opatom: Parser[Expr] =
    positioned {
      "true" ^^^ B(true) |
      "false" ^^^ B(false)
    } |
    super.opatom

  /** $booleanUop */
  abstract override def uop: Parser[Uop] =
    "!" ^^ { _ => Not } |
    super.uop

  /** Precedence: { `||` } < { `&&` }.
    *
    * $booleanBop
    */
  lazy val booleanBop: OpPrecedence = List(
    /* lowest */
    List("||" -> Or),
    List("&&" -> And)
    /* highest */
  )
}

/** The parser for just this boolean sub-language
  *
  * @see [[ParserLike]]
  * @author Bor-Yuh Evan Chang
  */
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
