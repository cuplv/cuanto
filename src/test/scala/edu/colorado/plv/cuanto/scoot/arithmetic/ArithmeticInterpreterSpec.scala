package edu.colorado.plv.cuanto.scoot
package arithmetic

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.{PropertyChecks, TableFor2}
import edu.colorado.plv.cuanto.scoot.ir._

class ArithmeticInterpreterSpec extends FlatSpec with Matchers with PropertyChecks {

  import Interpreter._
  private val jim = soot.jimple.Jimple.v()

  val va: Local = jim.newLocal("va", soot.IntType.v())
  val vb: Local = jim.newLocal("vb", soot.IntType.v())

  val testEnv: Interpreter.Env = Map((va, 3),(vb,15))

  val i1: IntConstant = soot.jimple.IntConstant.v(3)
  val a1: NegExpr = jim.newNegExpr(soot.jimple.IntConstant.v(2))
  val a2: AddExpr = jim.newAddExpr(jim.newLocal("va", soot.IntType.v()), soot.jimple.IntConstant.v(3))

  val exprLocalTests: TableFor2[Value, Int] = Table(
    "expression" -> "denotation",
    va -> 3,
    vb -> 15,
    i1 -> 3,
    a1 -> -2
  )

  "The Scoot interpreter" should "interpret Values containing in-scope Locals" in {
    forAll(exprLocalTests) { (e, n) =>
      denote(e, testEnv) should equal(Some(n))
    }
  }
}

/*class ArithmeticInterpreterSpec extends FlatSpec with Matchers with PropertyChecks {
  import Interpreter._
  import Builder._

  val va : Local = Builder.local("va")
  val vb : Local = Builder.local("vb")

  val testEnv: Interpreter.Env = Map((va,3),(vb,15))

  val exprTests : TableFor2[Value,Int]= Table(
    "expression" -> "denotation",
    convertIntConstant(int(1)) -> 1,
    convertNegExpr(neg(int(1))) -> -1,
    convertAddExpr(add(int(1))(int(1))) -> 2,
    convertSubExpr(sub(int(3))(int(1))) -> 2,
    convertMulExpr(mul(int(3))(int(2))) -> 6,
    convertDivExpr(div(int(8))(int(4))) -> 2
  )

  val exprLocalTests : TableFor2[Value, Int] = Table(
    "expression" -> "denotation",
    convertAddExpr(add(Builder.local("va"))(int(1))) -> 4,
    convertDivExpr(div(Builder.local("vb"))(Builder.local("va"))) -> 5
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
/*
  val other = local("other")

  val stmtTests = Table(
    "program" -> "denotation",
    Seq(assign(add(int(1))(int(1)))) -> 2,
    Seq(assign(int(0)), assign(int(5))) -> 5,
    Seq(assign(int(4)), subs(int(8)), muls(int(2))) -> -8,
    Seq(assign(int(1)), negs()) -> -1,
    Seq(assign(int(1), other),
      assign(add(int(1))(other))) -> 2
  )

  val stmtFailTests = Table(
    "bad program",
    Seq(adds(int(1)), assign(int(2))),
    Seq(assign(int(2),other)),
    Seq()
  )

  it should "interpret a variable mutated through a sequence of AssignStmts" in {
    forAll (stmtTests) { (e, n) => denote(e,acc) should equal (Some(n)) }
  }

  /** Here, "bad program" means that either the variable being tracked
    * is never assigned, or one or more expressions in the program
    * could not be interpreted */
  it should "give None when asked to interpret a bad program" in {
    forAll (stmtFailTests) { e => denote(e,acc) should equal (None) }
  }*/

}*/