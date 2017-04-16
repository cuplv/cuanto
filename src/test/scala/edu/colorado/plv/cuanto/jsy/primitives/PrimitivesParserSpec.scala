package edu.colorado.plv.cuanto.jsy
package primitives

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.arithmetic._
import edu.colorado.plv.cuanto.jsy.binding._
import edu.colorado.plv.cuanto.jsy.string._
import edu.colorado.plv.cuanto.jsy.primitives.Parser.parse
import edu.colorado.plv.cuanto.testing.implicits.tryEquality

/**
  * @author Bor-Yuh Evan Chang
  */
class PrimitivesParserSpec extends CuantoSpec {

  behavior of "jsy.primitives.Parser"

  val positives = Table(
    "concrete" -> "abstract",
    """{ let x = 3
      |  let y = "abc"
      |  x + y }
    """.stripMargin
      -> Bind(Var("x"), N(3), Bind(Var("y"), S("abc"), Binary(Plus, Var("x"), Var("y"))))
  )

  forAll (positives) { (conc, abs) =>
    it should s"parse $conc into $abs" in {
      parse(conc) shouldEqual abs
    }
  }

}
