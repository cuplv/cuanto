package edu.colorado.plv.cuanto.scoot.jimple
import soot.jimple

/**
  * Created by Jared on 6/21/2017.
  */
class AddExpr(dt: soot.jimple.AddExpr) extends BinopExpr(dt: soot.jimple.BinopExpr)
//empty interface

object AddExpr {
  def unapply(e: AddExpr): Option[(soot.Value, soot.Value)] =
    Some((e.op1, e.op2))
}