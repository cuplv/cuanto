package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/21/2017.
  */
class AddExpr(dt: soot.jimple.AddExpr) extends BinopExpr {
  def op1: Value = dt.getOp1()

  def op2: Value = dt.getOp2()
}
//empty interface

object AddExpr {
  def unapply(e: AddExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}