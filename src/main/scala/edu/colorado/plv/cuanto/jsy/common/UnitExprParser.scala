package edu.colorado.plv.cuanto.jsy
package common

import edu.colorado.plv.cuanto.parsing.RichParsers
import scala.util.parsing.combinator.{Parsers, RegexParsers}

/** Define a unit [[ExprParserLike]] that is
  * [[Parsers]].failure on everything.
  *
  * @author Kyle Headley
  */
trait UnitExprParser extends ExprParserLike { _: RichParsers with RegexParsers with Parsers =>
  override def expratom: Parser[Expr] = failure("expected atomic expression")
}
