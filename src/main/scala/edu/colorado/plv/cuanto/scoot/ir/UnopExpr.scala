package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class UnopExpr(dt: soot.jimple.UnopExpr) extends Expr(dt: soot.jimple.Expr) {
  def op: Value = new Value(dt.getOp())
}

object UnopExpr {
  def unapply(e: UnopExpr): Option[Value] = Some(e.op)
}