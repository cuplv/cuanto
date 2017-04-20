package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class MulExpr(dt: soot.jimple.MulExpr) extends BinopExpr(dt: soot.jimple.BinopExpr)
  //empty interface

object MulExpr {
  def unapply(e: MulExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}