package edu.colorado.plv.cuanto.scoot
package arithmetic

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks

class ArithmeticInterpreterSpec extends FlatSpec with Matchers with PropertyChecks {
  import Builder._
  import syntax._

  val denoteTests = Table(
    "expression" -> "denotation",
    buildAdd(buildVal(1),buildVal(1)) -> isVal(2),
    buildSub(buildVal(1),buildVal(1)) -> isVal(0),
    // multiplication
    // division
    // 3 addition
    // addition and multiplication
  )

  "denote" should "interpret 2" in {
    denote("2") shouldEqual 2
  }

  forAll (denoteTests) { (e, n) =>
    it should s"interpret $e to $n" in {
      denote(e) shouldEqual n
    }
  }

  behavior of "bigstep"

  forAll (denoteTests) { (e, n) =>
    it should s"interpret $e to $n" in {
      bigstep(e) shouldEqual N(n)
    }
  }

  behavior of "smallstep"

  forAll (denoteTests) { (e, n) =>
    it should s"interpret $e to $n" in {
      iterate[Expr](e)(smallstep) shouldEqual N(n)
    }
  }

  behavior of "machine"

  def evalMachine(e: Expr): Expr = {
    val id: Cont = identity
    val eq: ((Expr,Cont), (Expr,Cont)) => Boolean = { case ((e1,_), (e2, _)) => e1 == e2 }
    val (ep, _) = postfixedpoint( (e,id) )( machine )( eq )
    ep
  }

  forAll (denoteTests) { (e, n) =>
    it should s"interpret $e to $n" in {
      evalMachine(e) shouldEqual N(n)
    }
  }

}
