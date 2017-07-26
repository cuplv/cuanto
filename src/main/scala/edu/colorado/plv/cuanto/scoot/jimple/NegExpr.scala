package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Jared Wright
  */
class NegExpr private[jimple] (private val dt: soot.jimple.NegExpr) extends UnopExpr

object NegExpr {
  def unapply(e: NegExpr): Option[Value] = Some(e.dt.getOp())
}