package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Shawn Meier
  *         Created on 10/16/17.
  */
class GeExpr private[jimple] (private val dt: soot.jimple.GeExpr) extends BinopExpr

object GeExpr {
  def unapply(arg: GeExpr): Option[(Value,Value)] = Some(arg.dt.getOp1, arg.dt.getOp2)
}
