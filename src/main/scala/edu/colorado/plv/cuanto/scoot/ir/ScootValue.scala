package edu.colorado.plv.cuanto.scoot.ir

import soot.{Value, ValueBox, Type}
import scala.collection.JavaConverters._

/**
  * Created by Jared on 3/25/2017.
  */
class ScootValue(dt: Value) {
  def used: List[ScootValue] = dt.getUseBoxes().asScala.toList.map(a => new ScootValue(a.getValue()))

  def valType: Type = dt.getType()

  override def toString: String = dt.toString()
}
