package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/21/2017.
  */
class SubExpr(dt: soot.jimple.SubExpr) extends BinopExpr {
  def op1: Value = dt.getOp1()

  def op2: Value = dt.getOp2()
}

object SubExpr {
  def unapply(e: SubExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}
