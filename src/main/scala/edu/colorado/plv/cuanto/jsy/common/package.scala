package edu.colorado.plv.cuanto.jsy

import edu.colorado.plv.cuanto.parsing.{ParserLike, RichParsers}

import scala.util.parsing.combinator.JavaTokenParsers
import scala.util.parsing.input.Reader

/**
  * @author Bor-Yuh Evan Chang
  */
package object common {

  /** Trait for a JavaScripty parser. */
  trait JsyParserLike extends JavaTokenParsers with RichParsers with ParserLike[Expr] {
    override def scan(in: Reader[Char]): Input = in
  }

}
