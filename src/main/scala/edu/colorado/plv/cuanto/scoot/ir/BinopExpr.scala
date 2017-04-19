package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class BinopExpr(dt: soot.jimple.BinopExpr) extends Expr(dt: soot.jimple.Expr) {
  def op1: Value = new Value(dt.getOp1())

  def op2: Value = new Value(dt.getOp2())

  def symbol: String = dt.getSymbol()
}

object BinopExpr {
  // Unapply needs to go in a "companion object", it seems
  def unapply(e: BinopExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}
