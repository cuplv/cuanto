package edu.colorado.plv.cuanto.jsy
package numerical

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.arithmetic._
import edu.colorado.plv.cuanto.jsy.boolean._
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors
import edu.colorado.plv.cuanto.jsy.numerical.Parser.parse

/**
  * @author Bor-Yuh Evan Chang
  */
class NumericalParserSpec extends CuantoSpec with ParserBehaviors {

  override lazy val positives = Table(
    "concrete" -> "abstract",
    /* constraints */
    "true !== false" -> Binary(Ne, B(true), B(false)),
    "3 <= false" -> Binary(Le, N(3), B(false)),
    /* mixed */
    "(2 + 3) === 4" -> Binary(Eq, Binary(Plus, N(2), N(3)), N(4)),
    "(true && false) !== false" -> Binary(Ne, Binary(And, B(true), B(false)), B(false)),
    /* precedence */
    "2 + 3 === 4" -> Binary(Eq, Binary(Plus, N(2), N(3)), N(4)),
    "true !== 4 < 5" -> Binary(Ne, B(true), Binary(Lt, N(4), N(5)))
  )

  "jsy.numerical.Parser" should behave like parser(parse)

}
