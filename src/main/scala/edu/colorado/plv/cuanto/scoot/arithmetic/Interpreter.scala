package edu.colorado.plv.cuanto.scoot
package arithmetic

import soot._
import soot.jimple._

/** Implement an interpreter for soot objects that represent
  * arithemtic expressions
  */
object Interpreter {

  /** Interpreter for an arithmetic expression encoded as a series of
    * assignemnt statements.
    * 
    * For now, there is a single variable ([[Builder.acc]]) being
    * updated, which is the value that should be returned */
  def denote(b: Body): Int = ???

  /** Interpreter component for evaluating an arithmetic expression
    * encoded as a single `Value`
    * 
    * This has been deprecated because to make multi-operation
    * expressions, evaulation needs to be done over several assignment
    * statements (wrapped in a `Body`) */
  @deprecated
  def denote(v: Value): Int = v match {
    case e: IntConstant => e.value
    case e: UnopExpr => uop(e)(denote(e.getOp))
    case e: BinopExpr => bop(e)(denote(e.getOp1),denote(e.getOp2))
  }

  /** Interpret an arithmetic unary operator node, getting back a
    * function that performs the operation */
  def uop(op: UnopExpr): Int => Int = op match {
    case _: NegExpr => {_ * -1}
  }

  /** Interpret an arithemetic binary operator node, getting back a
    * function that performs the operation */
  def bop(op: BinopExpr): (Int, Int) => Int = op match {
    case _: AddExpr => {_ + _}
    case _: SubExpr => {_ - _}
    case _: MulExpr => {_ * _}
    case _: DivExpr => {_ / _}
  }

}
