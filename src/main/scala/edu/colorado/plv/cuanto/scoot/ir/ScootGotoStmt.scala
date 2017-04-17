package edu.colorado.plv.cuanto.scoot.ir

import soot.jimple.{Stmt, GotoStmt}

/**
  * Created by Jared on 4/17/2017.
  */
class ScootGotoStmt(dt: GotoStmt) extends ScootStmt(dt: Stmt) {
  def target: ScootStmt = new ScootStmt(dt.getTarget().asInstanceOf[Stmt])
}
