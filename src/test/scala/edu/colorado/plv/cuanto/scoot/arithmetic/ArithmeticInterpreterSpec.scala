package edu.colorado.plv.cuanto.scoot
package arithmetic

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks

class ArithmeticInterpreterSpec extends FlatSpec with Matchers with PropertyChecks {
  import Builder.{init, add, sub, mul, div, neg}

  val denoteTests = Table(
    "expression" -> "denotation",
    init(1) -> 1,
    neg(init(1)) -> -1,

    add(init(1),1) -> 2,
    sub(init(1),1) -> 0,
    mul(init(1),1) -> 1,
    div(init(1),1) -> 1,
    add(add(init(3),2),1) -> 6,
    add(mul(init(3),2),1) -> 7
  )

  forAll (denoteTests) { (e, n) =>
    Interpreter.denote(e) should equal (n)
  }

}
