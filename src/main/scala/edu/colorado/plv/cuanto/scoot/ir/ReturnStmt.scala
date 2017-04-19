package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class ReturnStmt(dt: soot.jimple.ReturnStmt) extends Stmt(dt: soot.jimple.Stmt) {
  def op: Value = new Value(dt.getOp())
}
