package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/11/2017.
  */
class Local(dt: soot.Local) extends Value(dt: soot.Value) {
  def name: String = dt.getName()
}
