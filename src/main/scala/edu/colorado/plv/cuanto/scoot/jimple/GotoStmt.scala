package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Shawn Meier
  *         Created on 10/4/17.
  */
class GotoStmt private[jimple] (private val dt: soot.jimple.GotoStmt) extends Stmt {
  override def getDt: soot.Unit = dt
}

object GotoStmt{
  def unapply(arg: GotoStmt): Option[Stmt] = Some(arg.dt.getTarget)
}
