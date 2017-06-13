package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 6/13/2017.
  */
abstract class BinopExpr(dt: soot.jimple.BinopExpr) extends Expr(dt: soot.jimple.Expr) {
  def op1: Value = dt.getOp1()

  def op2: Value = dt.getOp2()

  def symbol: String = dt.getSymbol()
}

object BinopExpr {
  // Unapply needs to go in a "companion object", it seems
  def unapply(e: BinopExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}