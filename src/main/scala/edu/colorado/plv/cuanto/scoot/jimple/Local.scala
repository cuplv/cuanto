package edu.colorado.plv.cuanto.scoot.jimple

/**
  * Created by Jared on 6/13/2017.
  */
class Local(dt: soot.Local) extends Value {
  def name: String = dt.getName()
}

object Local {
  def unapply(l: Local): Option[String] = Some(l.name)
}