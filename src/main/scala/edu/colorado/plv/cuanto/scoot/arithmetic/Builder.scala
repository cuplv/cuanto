package edu.colorado.plv.cuanto.scoot
package arithmetic

import soot.Value
import soot.jimple.{Jimple, Stmt, IntConstant}

/** Write convenience functions for constructing soot Value objects
  * that represent arithemtic expressions (for testing, mainly)
  */
object Builder {
  def int(i: Int): Value = IntConstant.v(i)

  def add(a: Int, b: Int): Value = Jimple.v().newAddExpr(int(a), int(b))
  def sub(a: Int, b: Int): Value = Jimple.v().newSubExpr(int(a), int(b))
  def mul(a: Int, b: Int): Value = Jimple.v().newMulExpr(int(a), int(b))
  def div(a: Int, b: Int): Value = Jimple.v().newDivExpr(int(a), int(b))
  def neg(a: Int):         Value = Jimple.v().newNegExpr(int(a))

  // When this executes, it terminates with an error saying "Box
  // ImmediateBox(null) cannot contain value: 1 + 2(class
  // soot.jimple.internal.JAddExpr)"
  // val asdf = new JAddExpr(new JAddExpr(int(1),int(2)), int(3))
}
