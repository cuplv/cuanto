package edu.colorado.plv.cuanto.scoot.ir

import soot.jimple.{Stmt, ReturnStmt}

/**
  * Created by Jared on 4/17/2017.
  */
class ScootReturnStmt(dt: ReturnStmt) extends ScootStmt(dt: Stmt) {
  def op: ScootValue = new ScootValue(dt.getOp())
}
