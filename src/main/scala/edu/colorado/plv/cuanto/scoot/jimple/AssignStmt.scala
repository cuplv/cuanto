package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Shawn Meier
  *         Created on 10/3/17.
  */
class AssignStmt private[jimple] (private val dt: soot.jimple.AssignStmt) extends Stmt {
  override def getDt: soot.Unit = dt
}

object AssignStmt{
  def unapply(e: AssignStmt): Option[(Value,Value)] = Some(e.dt.getLeftOp, e.dt.getRightOp)
}
