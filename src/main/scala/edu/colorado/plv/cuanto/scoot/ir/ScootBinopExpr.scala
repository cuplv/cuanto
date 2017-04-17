package edu.colorado.plv.cuanto.scoot.ir

import soot.jimple.{Expr, BinopExpr}

/**
  * Created by Jared on 4/17/2017.
  */
class ScootBinopExpr(dt: BinopExpr) extends ScootExpr(dt: Expr) {
  def op1: ScootValue = new ScootValue(dt.getOp1())

  def op2: ScootValue = new ScootValue(dt.getOp2())

  def symbol: String = dt.getSymbol()
}
