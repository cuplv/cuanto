package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Jared Wright
  */
class SubExpr private[jimple] (private val dt: soot.jimple.SubExpr) extends BinopExpr

object SubExpr {
  def unapply(e: SubExpr): Option[(Value, Value)] =
    Some((e.dt.getOp1(), e.dt.getOp2()))
}
