package edu.colorado.plv.cuanto.jsy
package arithmetic

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.arithmetic.Parser.parse
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors
import edu.colorado.plv.cuanto.testing.implicits.tryEquality

class ArithmeticParserSpec extends CuantoSpec with ParserBehaviors {

  "jsy.arithmetic.Parser" should "parse 3.14" in {
    parse("3.14") shouldEqual N(3.14)
  }

  it should "parse 3" in {
    assertResult(N(3)) {
      parse("3").get
    }
  }

  override lazy val positives = Table(
    "concrete" -> "abstract",
    "x" -> Var("x"),
    "(3.14)" -> N(3.14),
    "1 + 1" -> Binary(Plus, N(1), N(1)),
    "1 - 1" -> Binary(Minus, N(1), N(1)),
    "1 * 1" -> Binary(Times, N(1), N(1)),
    "1 / 1" -> Binary(Div, N(1), N(1)),
    "1 + 2 + 3" -> Binary(Plus, Binary(Plus, N(1), N(2)), N(3)),
    "1 + 2 * 3" -> Binary(Plus, N(1), Binary(Times, N(2), N(3)))
  )

  override lazy val negatives = Table(
    "concrete",
    "-",
    "-+*"
  )

  it should behave like parser(parse)

}
