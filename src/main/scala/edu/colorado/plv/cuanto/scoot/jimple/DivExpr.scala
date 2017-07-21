package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/21/2017.
  */
class DivExpr(dt: soot.jimple.DivExpr) extends BinopExpr(dt: soot.jimple.BinopExpr)
//empty interface

object DivExpr {
  def unapply(e: DivExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}