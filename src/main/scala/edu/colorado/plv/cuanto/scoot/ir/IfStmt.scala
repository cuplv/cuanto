package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class IfStmt(dt: soot.jimple.IfStmt) extends Stmt(dt: soot.jimple.Stmt) {
  def condition: Value = new Value(dt.getCondition())

  def target: Stmt = new Stmt(dt.getTarget())
}
