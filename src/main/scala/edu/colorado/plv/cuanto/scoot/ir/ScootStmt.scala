package edu.colorado.plv.cuanto.scoot.ir

import soot.jimple.Stmt
import scala.collection.JavaConverters._

/**
  * Created by Jared on 3/25/2017.
  */
class ScootStmt(dt: Stmt) {
  //Stmt-specific first, followed by general Unit functions
  //def invokeExpr: Option[ScootInvokeExpr] = Option(dt.getInvokeExpr())

  //def arrayRef: Option[ScootArrayRef] = Option(dt.getArrayRef())

  //def fieldRef: Option[ScootFieldRef] = Option(dt.getFieldRef())

  //now general Unit functions
  def useBoxes: List[ScootValue] = dt.getUseBoxes().asScala.toList.map(a => new ScootValue(a.getValue()))

  def defBoxes: List[ScootValue] = dt.getDefBoxes().asScala.toList.map(a => new ScootValue(a.getValue()))

  def unitBoxes: List[ScootStmt] = dt.getUnitBoxes().asScala.toList.map(a => new ScootStmt(a.getUnit()))

  def fallsThrough: Boolean = dt.fallsThrough()

  def branches: Boolean = dt.branches()
}
