package edu.colorado.plv.cuanto.scoot
package concrete_interpreter

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks
import edu.colorado.plv.cuanto.scoot.jimple._

class ArithmeticInterpreterSpec extends FlatSpec with Matchers with PropertyChecks {
  import Builder._
  import Interpreter._

  val va = local("va")
  val vb = local("vb")

  val testEnv : Map[String, Int] = Map(("va",3),("vb",15))

  val exprTests = Table(
    "expression" -> "denotation",
    int(1) -> 1,
    neg(int(1)) -> -1,
    add(int(1))(int(1)) -> 2,
    sub(int(3))(int(1)) -> 2,
    mul(int(3))(int(2)) -> 6,
    div(int(8))(int(4)) -> 2
  )

  val e1 : AddExpr = add(va)(int(1))
  val e2 : DivExpr = div(vb)(va)

  val exprLocalTests = Table(
    "expression" -> "denotation",
    e1 -> 4,
    e2 -> 5
  )

  "The Scoot interpreter" should "interpret stateless Values" in {
    forAll (exprTests) { (e, n) =>
      denote(e,testEnv) should equal (Some(n))
    }
  }

  it should "interpret Values containing in-scope Locals" in {
    forAll (exprLocalTests) { (e, n) =>
      denote(e,testEnv) should equal (Some(n))
    }
  }

  it should "give None when asked to interpret undefined Locals" in {
    forAll (exprLocalTests) { (e, n) => denote(e) should equal (None) }
  }
}
