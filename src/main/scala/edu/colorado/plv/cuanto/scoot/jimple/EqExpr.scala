package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Shawn Meier
  *         Created on 10/4/17.
  */
class EqExpr private [jimple] (private val dt: soot.jimple.EqExpr) extends BinopExpr

object EqExpr {
  def unapply(arg: EqExpr): Option[(Value,Value)] = Some(arg.dt.getOp1, arg.dt.getOp2)
}
