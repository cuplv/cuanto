package edu.colorado.plv.cuanto.scoot.jimple

import soot.SootMethod

import scala.collection.JavaConverters._
/**
  * @author Shawn Meier
  *         Created on 10/19/17.
  */
class StaticInvokeExpr private[jimple] (private val dt: soot.jimple.StaticInvokeExpr) extends Expr

object StaticInvokeExpr {
  def unapply(arg: StaticInvokeExpr) = {
    val method: SootMethod = arg.dt.getMethod
    val methodref = arg.dt.getMethodRef

    Some(arg.dt.getMethod, arg.dt.getArgs.asScala.toList.map( convertValue ))
  }
}