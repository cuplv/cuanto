package edu.colorado.plv.cuanto.scoot
package arithmetic

import soot._
import soot.jimple._

/** Implement an interpreter for soot objects that represent
  * arithemtic expressions
  */
object Interpreter {

  def denote(v: Value): Int = v match {
    case e: IntConstant => e.value
    case e: UnopExpr => uop(e)(denote(e.getOp))
    case e: BinopExpr => bop(e)(denote(e.getOp1),denote(e.getOp2))
  }

  def uop(op: UnopExpr): Int => Int = op match {
    case _: NegExpr => {_ * -1}
  }

  def bop(op: BinopExpr): (Int, Int) => Int = op match {
    case _: AddExpr => {_ + _}
    case _: SubExpr => {_ - _}
    case _: MulExpr => {_ * _}
    case _: DivExpr => {_ / _}
  }

}
