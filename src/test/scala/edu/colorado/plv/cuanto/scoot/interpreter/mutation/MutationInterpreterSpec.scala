package edu.colorado.plv.cuanto.scoot
package interpreter.mutation

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks

import domains._

////////////////////////////////////////////////////////////////////////

class MutationInterpreterSpec extends FlatSpec with Matchers
    with PropertyChecks {
  import interpreter.expression.Builder._

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
    forAll (stmtTests) { (e, n) =>
      mutation.interpret(e,acc.getName()) should equal (Some(Arith[IntDom](IntDom(n)))) }
  }

  /** Here, "bad program" means that either the variable being tracked
    * is never assigned, or one or more expressions in the program
    * could not be interpreted */
  it should "give None when asked to interpret a bad program" in {
    forAll (stmtFailTests) { e => mutation.interpret(e,acc.getName()) should equal (None) }
  }
}
