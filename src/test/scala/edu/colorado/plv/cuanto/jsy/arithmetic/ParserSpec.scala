package edu.colorado.plv.cuanto.jsy.arithmetic

import org.scalactic.Equality
import org.scalatest.prop.PropertyChecks
import org.scalacheck.Gen
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

class ParserSpec extends FlatSpec with Matchers with PropertyChecks {
  import edu.colorado.plv.cuanto.jsy.arithmetic.ast._
  import edu.colorado.plv.cuanto.jsy.arithmetic.Parser.parse

  implicit def tryEquality[T]: Equality[Try[T]] = { (a: Try[T], b: Any) =>
    Try(a.get == b.asInstanceOf[T]).getOrElse(false)
  }

  "jsy.arithmetic.Parser" should "parse 3.14" in {
    parse("3.14") shouldEqual N(3.14)
  }

  it should "parse 3" in {
    assertResult(N(3)) {
      parse("3").get
    }
  }

  val positives = Table(
    "concrete" -> "abstract",
    "(3.14)" -> N(3.14),
    "1 + 1" -> Binary(Plus, N(1), N(1)),
    "1 - 1" -> Binary(Minus, N(1), N(1)),
    "1 * 1" -> Binary(Times, N(1), N(1)),
    "1 / 1" -> Binary(Div, N(1), N(1)),
    "1 + 2 + 3" -> Binary(Plus, Binary(Plus, N(1), N(2)), N(3)),
    "1 + 2 * 3" -> Binary(Plus, N(1), Binary(Times, N(2), N(3)))
  )

  forAll (positives) { (conc, abs) =>
    it should s"parse $conc into $abs" in {
      parse(conc) shouldEqual abs
    }
  }

  it should "parse floating-point literals" in {
    forAll (Gen.posNum[Double]) { d =>
      parse(d.toString) shouldEqual N(d)
    }
  }

  it should "parse integer literals" in {
    forAll (Gen.posNum[Int]) { i =>
      parse(i.toString) shouldEqual N(i.toDouble)
    }
  }

  val negatives = Table(
    "concrete",
    "-",
    "-+*"
  )

  forAll (negatives) { conc =>
    it should s"not parse $conc" in {
      parse(conc) should be a 'failure
    }
  }

}
