package edu.colorado.plv.cuanto.scoot.ir

import soot.Local

/**
  * Created by Jared on 4/11/2017.
  */
class ScootLocal(override dt: Local) extends ScootValue{
  def name: String = dt.getName()
}
