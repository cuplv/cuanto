package edu.colorado.plv.cuanto.jsy.objects

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

trait ParserLike extends OpParserLike with JsyParserLike

/**
  * @author Bor-Yuh Evan Chang
  */
object Parser extends UnitOpParser with ParserLike {
  override val bop = ???
  override def expr = ???
  override def start = ???
}
