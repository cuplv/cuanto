package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class DivExpr(dt: soot.jimple.DivExpr) extends BinopExpr(dt: soot.jimple.BinopExpr)
  //empty interface

object DivExpr {
  def unapply(e: DivExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}