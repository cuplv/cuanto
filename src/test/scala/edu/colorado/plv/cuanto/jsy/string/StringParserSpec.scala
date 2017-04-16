package edu.colorado.plv.cuanto.jsy
package string

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors
import edu.colorado.plv.cuanto.jsy.string.Parser.parse

/**
  * @author Kyle Headley
  */
class StringParserSpec extends CuantoSpec with ParserBehaviors {

  override lazy val positives = Table(
    "concrete" -> "abstract",
    "\"hello\"" -> S("hello"),
    "\"world\"" -> S("world"),
    "\"one\" + \"one\"" -> Binary(Concat,S("one"),S("one"))
  )

  "jsy.string.Parser" should behave like parser(parse)

}
