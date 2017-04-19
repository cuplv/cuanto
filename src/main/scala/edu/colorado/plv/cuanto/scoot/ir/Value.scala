package edu.colorado.plv.cuanto.scoot.ir

import soot.Type
import scala.collection.JavaConverters._

/**
  * Created by Jared on 3/25/2017.
  */
class Value(dt: soot.Value) {
  def used: List[Value] = dt.getUseBoxes().asScala.toList.map(a => new Value(a.getValue()))

  def valType: Type = dt.getType()

  override def toString: String = dt.toString()
}
