package edu.colorado.plv.cuanto.jsy
package boolean

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.boolean.Parser.parse
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors
import edu.colorado.plv.cuanto.testing.implicits.tryEquality
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Bor-Yuh Evan Chang
  */
class BooleanParserSpec extends CuantoSpec with ParserBehaviors {

  override lazy val positives = Table(
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

  override lazy val flexibles = Table(
    "concrete" -> "abstract",
    "if (true) false else true" -> {
      If(B(true), B(false), B(true))
    },
    "if (true) false else if (true) false else true" -> {
      If(B(true), B(false), If(B(true), B(false), B(true)))
    }
  )

  "jsy.boolean.Parser" should behave like parser(parse)

}
