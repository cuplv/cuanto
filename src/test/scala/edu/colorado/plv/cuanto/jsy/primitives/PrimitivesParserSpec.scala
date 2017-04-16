package edu.colorado.plv.cuanto.jsy
package primitives

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.arithmetic._
import edu.colorado.plv.cuanto.jsy.binding._
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors
import edu.colorado.plv.cuanto.jsy.primitives.Parser.parse
import edu.colorado.plv.cuanto.jsy.string._

/**
  * @author Bor-Yuh Evan Chang
  */
class PrimitivesParserSpec extends CuantoSpec with ParserBehaviors {

  override lazy val positives = Table(
    "concrete" -> "abstract",
    """{ let x = 3
      |  let y = "abc"
      |  x + y }
    """.stripMargin
      -> Bind(Var("x"), N(3), Bind(Var("y"), S("abc"), Binary(Plus, Var("x"), Var("y"))))
  )

  "jsy.primitives.Parser" should behave like parser(parse)

}
