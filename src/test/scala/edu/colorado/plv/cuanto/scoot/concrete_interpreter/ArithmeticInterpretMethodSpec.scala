package edu.colorado.plv.cuanto.scoot
package concrete_interpreter

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks
import edu.colorado.plv.cuanto.scoot.jimple._

import scala.collection.immutable.HashMap
import scala.util.{Failure, Try}

class ArithmeticInterpretMethodSpec extends FlatSpec with Matchers with PropertyChecks {
  import Builder._
  import Interpreter._

  val va = local("va")
  val vb = local("vb")

  val testEnv  = StackFrame(None, Map(("va",CInteger(3)),("vb",CInteger(15))), None)

  val exprTests = Table(
    "expression" -> "denotation",
    int(1) -> CInteger(1),
    neg(int(1)) -> CInteger(-1),
    add(int(1))(int(1)) -> CInteger(2),
    sub(int(3))(int(1)) -> CInteger(2),
    mul(int(3))(int(2)) -> CInteger(6),
    div(int(8))(int(4)) -> CInteger(2)
  )

  val e1 : AddExpr = add(va)(int(1))
  val e2 : DivExpr = div(vb)(va)

  val exprLocalTests = Table(
    "expression" -> "denotation",
    e1 -> CInteger(4),
    e2 -> CInteger(5)
  )

  "The Scoot interpreter" should "interpret stateless Values" in {
    forAll (exprTests) { (e, n) =>
      evaluate_expr(e, testEnv) should equal (Some(n))
    }
  }

  it should "interpret Values containing in-scope Locals" in {
    forAll (exprLocalTests) { (e, n) =>
      val maybeValue = evaluate_expr(e, testEnv)
      maybeValue should equal (Some(n))
    }
  }

  it should "give None when asked to interpret undefined Locals" in {
    forAll (exprLocalTests) { (e, n) => assert(Try(evaluate_expr(e,StackFrame(None,new HashMap[String,CValue](), None))).isInstanceOf[Failure[RuntimeException]]) }
  }
}
