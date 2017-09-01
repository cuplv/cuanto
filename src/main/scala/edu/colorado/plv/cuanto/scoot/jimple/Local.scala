package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Jared Wright
  */
class Local private[jimple] (private val dt: soot.Local) extends Value

object Local {
  def unapply(l: Local): Option[String] = Some(l.dt.getName())
}