package edu.colorado.plv.cuanto.scoot
package interpreter.expression

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks

import domains._

////////////////////////////////////////////////////////////////////////

class ExpressionInterpreterSpec extends FlatSpec with Matchers
    with PropertyChecks {
  import Builder._

  val va = local("va")
  val vb = local("vb")

  val testEnv = Map(("va",Arith[IntDom](IntDom(3))),("vb",Arith[IntDom](IntDom(15))))

  val exprTests = Table(
    "expression" -> "denotation",
    int(1) -> 1,
    neg(int(1)) -> -1,
    add(int(1))(int(1)) -> 2,
    sub(int(3))(int(1)) -> 2,
    mul(int(3))(int(2)) -> 6,
    div(int(8))(int(4)) -> 2
  )

  val exprLocalTests = Table(
    "expression" -> "denotation",
    add(va)(int(1)) -> 4,
    div(vb)(va) -> 5
  )

  "The Scoot interpreter" should "interpret stateless Values" in {
    forAll (exprTests) { (e, n) =>
      interpret(e,testEnv) should equal (Some(Arith[IntDom](IntDom(n))))
    }
  }

  it should "interpret Values containing in-scope Locals" in {
    forAll (exprLocalTests) { (e, n) =>
      interpret(e,testEnv) should equal (Some(Arith[IntDom](IntDom(n))))
    }
  }

  it should "give None when asked to interpret undefined Locals" in {
    forAll (exprLocalTests) { (e, n) => interpret(e) should equal (None) }
  }


}
