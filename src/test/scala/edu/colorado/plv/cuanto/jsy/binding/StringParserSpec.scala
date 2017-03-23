package edu.colorado.plv.cuanto.jsy
package binding

import edu.colorado.plv.cuanto.jsy.binding.Parser.parse
import edu.colorado.plv.cuanto.testing.implicits.tryEquality
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Kyle Headley
  */
class BindingParserSpec extends FlatSpec with Matchers with PropertyChecks {

  behavior of "jsy.binding.Parser"

  val positives = Table(
    "concrete" -> "abstract",
    "two" -> Var("two"),
    "Let three = two in six" -> Bind(Var("three"),Var("two"),Var("six"))
  )

  forAll (positives) { (conc, abs) =>
    it should s"parse $conc into $abs" in {
      parse(conc) shouldEqual abs
    }
  }

}
