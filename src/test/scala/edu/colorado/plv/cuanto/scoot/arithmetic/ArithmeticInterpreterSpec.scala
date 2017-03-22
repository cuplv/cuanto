package edu.colorado.plv.cuanto.scoot
package arithmetic

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks

class ArithmeticInterpreterSpec extends FlatSpec with Matchers with PropertyChecks {
  import Interpreter._
  import Builder._

  val denoteTests = Table(
    "expression" -> "denotation",
    int(1) -> 1,

    add(1,1) -> 2,
    sub(1,1) -> 0,
    mul(1,1) -> 1,
    div(1,1) -> 1
    // add(int(1),add(int(2),int(3))) -> 6,
    // add(int(1),mul(int(2),int(3))) -> 7
  )

  forAll (denoteTests) { (e, n) =>
    it should s"interpret $e to $n" in {
      denote(e) shouldEqual n
    }
  }

}
