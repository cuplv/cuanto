package edu.colorado.plv.cuanto.jsy.refutation

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy._

/**
  * @author Benno Stein
  */
class RefutationSpec extends CuantoSpec {
  /** Helpers for demonstrations */
  def assertVarTrue(v : String) : AState = new AState(True, Pure(Var(v)::Nil))
  def stmtAssignFalse(v : String) : Expr = Binary(mutation.Assign, Var(v), boolean.B(false))
  def stmtAssignTrue(v : String) : Expr = Binary(mutation.Assign, Var(v), boolean.B(true))
  def seq(expressions: Expr*) = expressions.reduce { (e1,e2) => Binary(binding.Sequ, e1, e2)}

  "The JSY refutation analysis" should "make simple refutations" in {
    assertResult(true) { JsyThresher.canRefute(assertVarTrue("x"), stmtAssignFalse("x")) }
  }

  it should "make simple witnesses" in {
    assertResult(false) { JsyThresher.canRefute(assertVarTrue("x"), stmtAssignTrue("x")) }
  }

  it should "be flow sensitive" in {
    assertResult(true) {JsyThresher.canRefute(assertVarTrue("x"), seq(stmtAssignTrue("x"), stmtAssignFalse("x")))}
    assertResult(false) {JsyThresher.canRefute(assertVarTrue("x"), seq(stmtAssignFalse("x"), stmtAssignTrue("x")))}
  }

  it should "be path sensitive" in {
    assertResult(true) {JsyThresher.canRefute(assertVarTrue("x"),
      seq(// y := true ; if(y) {x := false} else {x := true} ;
        stmtAssignTrue("y"),
        boolean.If(
          Var("y"),
          stmtAssignFalse("x"),
          stmtAssignTrue("x")
        )
      )
    )}
    println("=="*80)
    assertResult(false) {JsyThresher.canRefute(assertVarTrue("x"),
      seq(// y := false ; if(y) {x := false} else {x := true} ;
        stmtAssignFalse("y"),
        boolean.If(
          Var("y"),
          stmtAssignFalse("x"),
          stmtAssignTrue("x")
        )
      )
    )}



  }
}