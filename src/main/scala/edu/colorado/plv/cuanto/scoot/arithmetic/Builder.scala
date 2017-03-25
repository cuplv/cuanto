package edu.colorado.plv.cuanto.scoot
package arithmetic

import soot.{Value, IntType, Local, Immediate}
import soot.jimple.{Jimple, AssignStmt, IntConstant, AddExpr, SubExpr,
  MulExpr, DivExpr, NegExpr}

/** Write convenience functions for constructing soot Value objects
  * that represent arithemtic expressions (for testing, mainly)
  * 
  * This should largely be subsumed by the "Scoot" Scala-idiomatic
  * representation of Soot objects (coming soon)
  */
object Builder {
  private val jim = Jimple.v()

  /** The default accumulator variable for the interpreter to track */
  val acc = local("accumulator")

  /** Lift an `Int` into a `Value` */
  def int(i: Int): IntConstant = IntConstant.v(i)

  def local(name: String): Local = jim.newLocal(name, IntType.v())

  def assign(v: Value, l: Local = acc): AssignStmt = jim.newAssignStmt(l, v)

  def add(i1: Immediate)(i2: Immediate): AddExpr = jim.newAddExpr(i1,i2)
  def sub(i1: Immediate)(i2: Immediate): SubExpr = jim.newSubExpr(i1,i2)
  def mul(i1: Immediate)(i2: Immediate): MulExpr = jim.newMulExpr(i1,i2)
  def div(i1: Immediate)(i2: Immediate): DivExpr = jim.newDivExpr(i1,i2)
  def neg(i: Immediate): NegExpr = jim.newNegExpr(i)

  def adds(i: Immediate, l: Local = acc): AssignStmt = assign(add(l)(i),l)
  def subs(i: Immediate, l: Local = acc): AssignStmt = assign(sub(l)(i),l)
  def muls(i: Immediate, l: Local = acc): AssignStmt = assign(mul(l)(i),l)
  def divs(i: Immediate, l: Local = acc): AssignStmt = assign(div(l)(i),l)
  def negs(l: Local = acc): AssignStmt = assign(neg(l),l)

}
