package edu.colorado.plv.cuanto.scoot.ir

import scala.collection.JavaConverters._

/**
  * Created by Jared on 3/25/2017.
  */
class Stmt(dt: soot.jimple.Stmt) {
  //Stmt-specific first, followed by general Unit functions
  //def invokeExpr: Option[ScootInvokeExpr] = Option(dt.getInvokeExpr())

  //def arrayRef: Option[ScootArrayRef] = Option(dt.getArrayRef())

  //def fieldRef: Option[ScootFieldRef] = Option(dt.getFieldRef())

  //now general Unit functions
  def useBoxes: List[Value] = dt.getUseBoxes().asScala.toList.map(a => new Value(a.getValue()))

  def defBoxes: List[Value] = dt.getDefBoxes().asScala.toList.map(a => new Value(a.getValue()))

  def unitBoxes: List[Stmt] = dt.getUnitBoxes().asScala.toList.map(a => new Stmt(a.getUnit().asInstanceOf[soot.jimple.Stmt]))

  def fallsThrough: Boolean = dt.fallsThrough()

  def branches: Boolean = dt.branches()
}
