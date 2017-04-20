package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class AddExpr(dt: soot.jimple.AddExpr) extends BinopExpr(dt: soot.jimple.BinopExpr)
  //empty interface

object AddExpr {
  def unapply(e: AddExpr): Option[(Value, Value)] =
    Some((e.op1, e.op2))
}