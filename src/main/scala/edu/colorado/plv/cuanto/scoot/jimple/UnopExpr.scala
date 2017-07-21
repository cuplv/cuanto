package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/13/2017.
  */
abstract class UnopExpr(dt: soot.jimple.UnopExpr) extends Expr(dt: soot.jimple.Expr) {
  def op: Value = dt.getOp()
}

object UnopExpr {
  def unapply(e: UnopExpr): Option[Value] = Some(e.op)
}