package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class GotoStmt(dt: soot.jimple.GotoStmt) extends Stmt(dt: soot.jimple.Stmt) {
  def target: Stmt = new Stmt(dt.getTarget().asInstanceOf[soot.jimple.Stmt])
}
