package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/21/2017.
  */
class MulExpr(dt: soot.jimple.MulExpr) extends BinopExpr {
  def op1: Value = dt.getOp1()

  def op2: Value = dt.getOp2()
}

object MulExpr {
  def unapply(e: MulExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}