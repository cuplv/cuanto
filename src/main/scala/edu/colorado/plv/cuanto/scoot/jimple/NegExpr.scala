package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/13/2017.
  */
class NegExpr private[jimple] (private val dt: soot.jimple.NegExpr) extends UnopExpr

object NegExpr {
  def unapply(e: NegExpr): Option[Value] = Some(e.dt.getOp())
}