package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Jared Wright
  */
class AddExpr private[jimple] (private val dt: soot.jimple.AddExpr) extends BinopExpr
//empty interface

object AddExpr {
  def unapply(e: AddExpr): Option[(Value, Value)] =
    Some((e.dt.getOp1(), e.dt.getOp2()))
}