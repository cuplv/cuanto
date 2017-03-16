package edu.colorado.plv.cuanto.jsy
package common

import edu.colorado.plv.cuanto.parsing.{ParserLike, RichParsers}

import scala.util.parsing.combinator.JavaTokenParsers
import scala.util.parsing.input.Reader

/** Trait for a JavaScripty parser.
  *
  * @author Bor-Yuh Evan Chang
  */
trait JsyParserLike extends JavaTokenParsers with RichParsers with ParserLike[Expr] {
  override def scan(in: Reader[Char]): Input = in
}
