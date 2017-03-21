package edu.colorado.plv.cuanto.jsy
package string

import edu.colorado.plv.cuanto.jsy.string.Parser.parse
import edu.colorado.plv.cuanto.testing.implicits.tryEquality
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Kyle Headley
  */
class StringParserSpec extends FlatSpec with Matchers with PropertyChecks {

  behavior of "jsy.string.Parser"

  val positives = Table(
    "concrete" -> "abstract",
    "\"hello\"" -> S("hello"),
    "\"world\"" -> S("world"),
    "\"one\" + \"one\"" -> Binary(Concat,S("one"),S("one"))
  )



  forAll (positives) { (conc, abs) =>
    it should s"parse $conc into $abs" in {
      parse(conc) shouldEqual abs
    }
  }

}
