package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Jared Wright
  */
class IntConstant private[jimple] (private val dt: soot.jimple.IntConstant) extends Value

object IntConstant {
  def unapply(e: IntConstant): Option[Int] =
    Some(e.dt.value)
}