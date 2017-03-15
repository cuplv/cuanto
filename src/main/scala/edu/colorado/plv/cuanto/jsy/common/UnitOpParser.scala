package edu.colorado.plv.cuanto.jsy
package common

import edu.colorado.plv.cuanto.parsing.RichParsers
import scala.util.parsing.combinator.{Parsers, RegexParsers}

/** Define a unit [[OpParserLike]] that is [[Parsers.failure]] on everything.
  *
  * @author Bor-Yuh Evan Chang
  */
trait UnitOpParser extends OpParserLike { _: RichParsers with RegexParsers with Parsers =>
  override def opatom: Parser[Expr] = failure("expected opatom")
  override def uop: Parser[Uop] = failure("expected uop")
}
