package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/13/2017.
  */
class IntConstant(dt: soot.jimple.IntConstant) extends Value {
  def v : Int = dt.value
}

object IntConstant {
  def unapply(e: IntConstant): Option[Int] =
    Some(e.v)
}