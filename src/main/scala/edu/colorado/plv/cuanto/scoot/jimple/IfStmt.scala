package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Shawn Meier
  *         Created on 10/4/17.
  */
class IfStmt private[jimple] (private val dt: soot.jimple.IfStmt) extends Stmt {
  override def getDt: soot.Unit = dt
}
object IfStmt{
  def unapply(arg: IfStmt): Option[(Value,Stmt)] = Some(arg.dt.getCondition,arg.dt.getTarget)
}


