package edu.colorado.plv.cuanto.scoot.jimple

import scala.collection.JavaConverters._
import soot.Type

/**
  * Created by Jared on 6/13/2017.
  */
abstract class Value(dt: soot.Value) {
  def used: List[Value] = dt.getUseBoxes().asScala.toList.map(a => convertValue(a.getValue()))

  def valType: Type = dt.getType()

  override def toString: String = dt.toString()
}