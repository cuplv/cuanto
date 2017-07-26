package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/13/2017.
  */
class IntConstant private[jimple] (private val dt: soot.jimple.IntConstant) extends Value

object IntConstant {
  def unapply(e: IntConstant): Option[Int] =
    Some(e.dt.value)
}