package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Shawn Meier
  *         Created on 10/4/17.
  */
class NeExpr private[jimple] (private val dt: soot.jimple.NeExpr) extends Expr

object NeExpr{
  def unapply(arg: NeExpr): Option[(Value, Value)] = Some(arg.dt.getOp1,arg.dt.getOp2)
}
