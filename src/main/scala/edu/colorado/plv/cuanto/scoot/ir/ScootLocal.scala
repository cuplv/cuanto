package edu.colorado.plv.cuanto.scoot.ir

import soot.{Local, Value}

/**
  * Created by Jared on 4/11/2017.
  */
class ScootLocal(dt: Local) extends ScootValue(dt: Value) {
  def name: String = dt.getName()
}
