package edu.colorado.plv.cuanto.jsy
package boolean

import edu.colorado.plv.cuanto.jsy.boolean.Parser.parse
import edu.colorado.plv.cuanto.testing.implicits.tryEquality
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Bor-Yuh Evan Chang
  */
class ParserSpec extends FlatSpec with Matchers with PropertyChecks {

  behavior of "jsy.boolean.Parser"

  val positives = Table(
    "concrete" -> "abstract",
    "true" -> B(true),
    /* precedence: || < && */
    "true || false && true" -> {
      Binary(Or,
        B(true),
        Binary(And, B(false), B(true))
      )
    }
  )

  forAll (positives) { (conc, abs) =>
    it should s"parse $conc into $abs" in {
      parse(conc) shouldEqual abs
    }
  }

}
