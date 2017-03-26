package edu.colorado.plv.cuanto.scoot.ir

import soot.jimple.Stmt
import soot.{ValueBox, UnitBox}
import scala.collection.JavaConverters._

/**
  * Created by Jared on 3/25/2017.
  */
class ScootStmt(dt: Stmt) {
  //Stmt-specific first, followed by general Unit functions
  def invokeExpr: Option[ScootInvokeExpr] = Option(dt.getInvokeExpr())

  def arrayRef: Option[ScootArrayRef] = Option(dt.getArrayRef())

  def fieldRef: Option[ScootFieldRef] = Option(dt.getFieldRef())

  //now general Unit functions
  def useBoxes: List[ValueBox] = dt.getUseBoxes().asScala.toList

  def defBoxes: List[ValueBox] = dt.getDefBoxes().asScala.toList

  def unitBoxes: List[UnitBox] = dt.getUnitBoxes().asScala.toList

  def fallsThrough: Boolean = dt.fallsThrough()

  def branches: Boolean = dt.branches()
}
