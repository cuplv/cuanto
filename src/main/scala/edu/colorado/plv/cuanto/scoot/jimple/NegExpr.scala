package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/13/2017.
  */
class NegExpr(dt: soot.jimple.NegExpr) extends UnopExpr(dt: soot.jimple.UnopExpr)
//empty interface

object NegExpr {
  def unapply(e: NegExpr): Option[Value] = Some(e.op)
}