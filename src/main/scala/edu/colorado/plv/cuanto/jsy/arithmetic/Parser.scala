package edu.colorado.plv.cuanto.jsy
package arithmetic

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse into the arithmetic sub-language.
  *
  * Relies on [[edu.colorado.plv.cuanto.jsy.common.OpParserLike]] to parse unary and binary expressions.
  *
  * The atoms are floating pointer literals:
  *
  * $arithmeticOpatom
  *
  * The unary and binary operators are negation, plus, minus, times,
  * and divide:
  *
  * $arithmeticUop
  *
  * $arithmeticBop
  *
  * @define arithmeticOpatom ''opatom'' ::= ''float''
  * @define arithmeticUop ''uop'' ::= '-'
  * @define arithmeticBop ''bop'' ::= '+' | '-' | '*' | '/'
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  /** $arithmeticOpatom */
  abstract override def opatom: Parser[Expr] =
    positioned {
      floatingPointNumber ^^ (s => N(s.toDouble))
    } |
    super.opatom

  /** $arithmeticUop */
  abstract override def uop: Parser[Uop] =
    "-" ^^ { _ => Neg } |
    super.uop

  /** Precedence: { '+', '-' } < { '*', '/' }.
    *
    * $arithmeticBop
    */
  lazy val arithmeticBop: OpPrecedence = List(
      /* lowest */
      List("+" -> Plus, "-" -> Minus),
      List("*" -> Times, "/" -> Div)
      /* highest */
  )
}

/** The parser for just this arithmetic sub-language.
  *
  * @see [[ParserLike]]
  * @author Bor-Yuh Evan Chang
  */
object Parser extends UnitOpParser with ParserLike {
  override def start: Parser[Expr] = binary

  /** Define precedence of left-associative binary operators. */
  override lazy val bop: OpPrecedence = arithmeticBop
}
