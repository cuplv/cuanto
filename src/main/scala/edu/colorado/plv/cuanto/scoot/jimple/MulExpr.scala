package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/21/2017.
  */
class MulExpr private[jimple] (private val dt: soot.jimple.MulExpr) extends BinopExpr

object MulExpr {
  def unapply(e: MulExpr): Option[(Value, Value)] =
    Some((e.dt.getOp1(), e.dt.getOp2()))
}