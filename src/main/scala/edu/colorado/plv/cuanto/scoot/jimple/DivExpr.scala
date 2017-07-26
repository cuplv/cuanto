package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/21/2017.
  */
class DivExpr private[jimple] (private val dt: soot.jimple.DivExpr) extends BinopExpr

object DivExpr {
  def unapply(e: DivExpr): Option[(Value, Value)] =
    Some((e.dt.getOp1(), e.dt.getOp2()))
}