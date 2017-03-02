package edu.colorado.plv.cuanto.jsy.arithmetic

import org.scalactic.Equality
import org.scalatest._
import org.scalatest.prop.PropertyChecks

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

  "jsy.arithmetic.Parser" should "parse (3.14)" in {
    parse("(3.14)") shouldEqual N(3.14)
  }

  it should "parse floating-point literals" in {
    forAll { (d: Double) =>
      parse(d.toString) shouldEqual N(d)
    }
  }

  it should "parse integer literals" in {
    forAll { (i: Int) =>
      parse(i.toString) shouldEqual N(i.toDouble)
    }
  }

}
