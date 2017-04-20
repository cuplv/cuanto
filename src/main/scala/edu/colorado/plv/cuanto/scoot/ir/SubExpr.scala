package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class SubExpr(dt: soot.jimple.SubExpr) extends BinopExpr(dt: soot.jimple.BinopExpr)
  //empty interface

object SubExpr {
  def unapply(e: SubExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}