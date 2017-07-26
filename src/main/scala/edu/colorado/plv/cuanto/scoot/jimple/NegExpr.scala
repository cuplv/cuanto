package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/13/2017.
  */
class NegExpr(dt: soot.jimple.NegExpr) extends UnopExpr{
  def op: Value = dt.getOp()
}

object NegExpr {
  def unapply(e: NegExpr): Option[Value] = Some(e.op)
}