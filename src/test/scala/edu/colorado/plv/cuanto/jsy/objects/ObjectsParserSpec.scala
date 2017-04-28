package edu.colorado.plv.cuanto.jsy
package objects

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors

/**
  * @author Bor-Yuh Evan Chang
  */
class ObjectsParserSpec extends CuantoSpec with ParserBehaviors {

  override lazy val positives = Table(
    "concrete" -> "abstract"
  )

  "jsy.objects.Parser" should behave like parser(Parser.parse)
}