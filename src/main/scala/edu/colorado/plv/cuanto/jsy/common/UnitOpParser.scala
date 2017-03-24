package edu.colorado.plv.cuanto.jsy
package common

import edu.colorado.plv.cuanto.parsing.RichParsers
import scala.util.parsing.combinator.{Parsers, RegexParsers}

/** Define a unit [[edu.colorado.plv.cuanto.jsy.common.OpParserLike]] that is
  * [[scala.util.parsing.combinator.Parsers]].failure on everything.
  *
  * @author Bor-Yuh Evan Chang
  */
trait UnitOpParser extends OpParserLike { _: RichParsers with RegexParsers with Parsers =>
  override def opatom: Parser[Expr] = failure("expected opatom")
  override def uop: Parser[Uop] = failure("expected uop")
}
