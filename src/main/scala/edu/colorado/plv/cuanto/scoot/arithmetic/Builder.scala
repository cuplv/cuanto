package edu.colorado.plv.cuanto.scoot
package arithmetic

import soot._
import soot.jimple._
import soot.jimple.internal._

/** Write convenience functions for constructing soot Value objects
  * that represent arithemtic expressions (for testing, mainly)
  */
object Builder {
  def int(i: Int): Value = IntConstant.v(i)

  def add(a: Value, b: Value): Value = new JAddExpr(a,b)
  def sub(a: Value, b: Value): Value = new JSubExpr(a,b)
  def mul(a: Value, b: Value): Value = new JMulExpr(a,b)
  def div(a: Value, b: Value): Value = new JDivExpr(a,b)

  def neg(a: Value): Value = new JNegExpr(a)

  // When this executes, it terminates with an error saying "Box
  // ImmediateBox(null) cannot contain value: 1 + 2(class
  // soot.jimple.internal.JAddExpr)"
  val asdf = new JAddExpr(new JAddExpr(int(1),int(2)), int(3))
}
