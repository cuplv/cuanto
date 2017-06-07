package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 6/6/2017.
  */
class IntConstant(dt: soot.jimple.IntConstant) extends Value(dt: soot.Value) {
  def v = dt.value
}

object IntConstant {
  def unapply(e: IntConstant): Option[Int] =
    Some(e.v)
}