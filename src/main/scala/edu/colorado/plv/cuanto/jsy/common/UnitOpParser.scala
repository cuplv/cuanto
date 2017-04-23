package edu.colorado.plv.cuanto.jsy
package common

/** Define a unit [[edu.colorado.plv.cuanto.jsy.common.OpParserLike]] that is
  * [[scala.util.parsing.combinator.Parsers]].failure on everything.
  *
  * @author Bor-Yuh Evan Chang
  */
trait UnitOpParser extends OpParserLike {
  override def opatom: Parser[Expr] = failure("expected opatom")
  override def uop: Parser[Uop] = failure("expected uop")
  override def optyp: Parser[Typ] = failure("expected optyp")
}
