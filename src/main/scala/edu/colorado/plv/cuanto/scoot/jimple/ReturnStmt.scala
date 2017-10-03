package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Shawn Meier
  *         Created on 10/3/17.
  */
class ReturnStmt private[jimple] (private val dt: soot.jimple.ReturnStmt) extends Stmt{
  override def getDt: soot.Unit = dt
}

object ReturnStmt {
  def unapply(e: ReturnStmt): Option[Value] = {
    Some(e.dt.getOp)
  }
}
