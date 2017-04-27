package edu.colorado.plv.cuanto.jsy
package common

/** Define a unit [[edu.colorado.plv.cuanto.jsy.common.OpParserLike]] that is
  * [[scala.util.parsing.combinator.Parsers]].failure on everything.
  *
  * @author Bor-Yuh Evan Chang
  */
trait UnitOpParser extends OpParserLike {
  override def opAtom: Parser[Expr] = failure("expected opatom")
  override def uop: Parser[Uop] = failure("expected uop")
  override def opTyp: Parser[Typ] = failure("expected optyp")
}
