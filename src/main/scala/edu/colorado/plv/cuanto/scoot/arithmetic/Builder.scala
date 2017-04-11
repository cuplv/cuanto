package edu.colorado.plv.cuanto.scoot
package arithmetic

import soot.{Body, Value, IntType, Unit => SootUnit}
import soot.jimple.{Jimple, IntConstant}

/** Write convenience functions for constructing soot Value objects
  * that represent arithemtic expressions (for testing, mainly)
  * 
  * This should largely be subsumed by the Scoot representation of
  * Soot objects (coming soon)
  */
object Builder {
  val jim = Jimple.v()

  /** The accumulator variable that the interpreter should track */
  val acc = jim.newLocal("accumulator", IntType.v())

  /** Lift an `Int` into a `Value` */
  def int(i: Int): Value = IntConstant.v(i)

  /** Append a unit onto a body (this mutates the original body...) */
  def appUnit(b: Body, u: SootUnit): Body = {
    b.getUnits().add(u)
    return b // seeems chains can only be constructed by mutation >_<
  }

  /** Create a body with a single assignment (to the accumulator) */
  def init(i: Int): Body =
    appUnit(jim.newBody(),jim.newAssignStmt(acc, int(i)))

  /** Append a statement that adds `i` to the accumulator */
  def add(b: Body, i: Int) =
    appUnit(b,jim.newAssignStmt(acc, jim.newAddExpr(acc, int(i))))

  /** Append a statement that adds `i` to the accumulator */
  def sub(b: Body, i: Int) =
    appUnit(b,jim.newAssignStmt(acc, jim.newSubExpr(acc, int(i))))

  /** Append a statement that adds `i` to the accumulator */
  def mul(b: Body, i: Int) =
    appUnit(b,jim.newAssignStmt(acc, jim.newMulExpr(acc, int(i))))

  /** Append a statement that adds `i` to the accumulator */
  def div(b: Body, i: Int) =
    appUnit(b,jim.newAssignStmt(acc, jim.newDivExpr(acc, int(i))))

  /** Append a statement that adds `i` to the accumulator */
  def neg(b: Body) =
    appUnit(b,jim.newAssignStmt(acc, jim.newNegExpr(acc)))
}
