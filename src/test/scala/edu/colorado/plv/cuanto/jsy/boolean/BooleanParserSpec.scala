package edu.colorado.plv.cuanto.jsy
package boolean

import edu.colorado.plv.cuanto.jsy.boolean.Parser.parse
import edu.colorado.plv.cuanto.testing.implicits.tryEquality
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Bor-Yuh Evan Chang
  */
class BooleanParserSpec extends FlatSpec with Matchers with PropertyChecks {

  behavior of "jsy.boolean.Parser"

  val positives = Table(
    "concrete" -> "abstract",
    "true" -> B(true),
    "false" -> B(false),
    "!false" -> Unary(Not, B(false)),
    "(true || false) && true" -> {
      Binary(And,
        Binary(Or, B(true), B(false)),
        B(true)
      )
    },
    /* precedence: || < && */
    "true || false && true" -> {
      Binary(Or,
        B(true),
        Binary(And, B(false), B(true))
      )
    },
    "true ? false : true" -> {
      If(B(true), B(false), B(true))
    },
    "if (true) { false } else { true }" -> {
      If(B(true), B(false), B(true))
    }
  )

  val flexibles = Table(
    "concrete" -> "abstract",
    "if (true) false else true" -> {
      If(B(true), B(false), B(true))
    },
    "if (true) false else if (true) false else true" -> {
      If(B(true), B(false), If(B(true), B(false), B(true)))
    }
  )

  forAll (positives) { (conc, abs) =>
    it should s"parse $conc into $abs" in {
      parse(conc) shouldEqual abs
    }
  }

  forAll (flexibles) { (conc, abs) =>
    it should s"flexibly parse $conc into $abs" in {
      parse(conc) shouldEqual abs
    }
  }

}
