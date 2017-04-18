package edu.colorado.plv.cuanto.jsy
package common

import edu.colorado.plv.cuanto.parsing.{ParserLike, RichParsers}

import scala.util.parsing.combinator.JavaTokenParsers
import scala.util.parsing.input.Reader

/** Common trait for a JavaScripty parser.
  *
  * Mixes [[scala.util.parsing.combinator.JavaTokenParsers]] for basic
  * tokens (e.g., identifiers, numbers) with some utilities in
  * [[RichParsers]] and a top-level interface in [[ParserLike]].
  *
  * @author Bor-Yuh Evan Chang
  */
trait JsyParserLike extends JavaTokenParsers with RichParsers with ParserLike[Expr] {
  override def scan(in: Reader[Char]): Input = in
}
