package edu.colorado.plv.cuanto.jsy
package boolean

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse into the boolean sub-language.
  *
  * Relies on [[edu.colorado.plv.cuanto.jsy.common.OpParserLike]] to parse unary and binary expressions.
  *
  * The atoms are `true`, `false`, and ''ifthenelse''.
  *
  * $booleanOpatom
  *
  * The unary and binary operators are negation, or, and and:
  *
  * $booleanUop
  *
  * $booleanBop
  *
  * The ternary ''e,,1,,'' `?` ''e,,2,,'' `:` ''e,,3,,'' has the lowest precedence.
  *
  * @define booleanIteop ''iteop'' ::=  ''binary'' `?` ''expr'' `:` ''itesub''
  * @define booleanIfthenelse ''ifthenelse'' ::= `if` ''e'' `then` ''e'' `else` ''itesub''
  * @define booleanOpatom ''opatom'' ::= `true` | `false` | `ifthenelse`
  * @define booleanUop ''uop'' ::= `!`
  * @define booleanBop ''bop'' ::= `||` | `&&`
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  /** Parameter: define the non-terminal for the sub-expressions
    * of the if-then-else expression, that is,
    *
    *   - $booleanIteop
    *   - $booleanIfthenelse
    */
  def itesub: Parser[Expr]

  def iteop: Parser[Expr] =
    binary ~ opt(withpos(("?" ~> expr) ~ (":" ~> itesub))) ^^ {
      case e1 ~ None => e1
      case e1 ~ Some((pos, e2 ~ e3)) => If(e1, e2, e3) setPos pos
    }

  /** $booleanOpatom */
  abstract override def opatom: Parser[Expr] =
    positioned {
      "true" ^^^ B(true) |
      "false" ^^^ B(false) |
      ifthenelse
    } |
    super.opatom

  def ifthenelse: Parser[Expr] =
    ("if" ~> parenthesized) ~ expr ~ ("else" ~> itesub) ^^ {
      case e1 ~ e2 ~ e3 => If(e1, e2, e3)
    }

  /** $booleanUop */
  abstract override def uop: Parser[Uop] =
    "!" ^^^ Not |
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

/** The parser for just this boolean sub-language.
  *
  * @see [[ParserLike]]
  * @author Bor-Yuh Evan Chang
  */
object Parser extends UnitOpParser with ParserLike {
  override def start: Parser[Expr] = expr
  override def expr: Parser[Expr] = iteop
  override def itesub: Parser[Expr] = iteop
  override lazy val bop: OpPrecedence = booleanBop
}
