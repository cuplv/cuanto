package edu.colorado.plv.cuanto.scoot.ir

import soot.{Value, ValueBox, Type}
import scala.collection.JavaConverters._

/**
  * Created by Jared on 3/25/2017.
  */
class ScootValue(dt: Value) {
  def used: List[ValueBox] = dt.getUseBoxes().asScala.toList

  def valType: Type = dt.getType()

  override def toString: String = dt.toString()
}
