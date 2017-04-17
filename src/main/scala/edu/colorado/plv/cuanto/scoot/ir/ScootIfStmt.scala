package edu.colorado.plv.cuanto.scoot.ir

import soot.jimple.{Stmt, IfStmt}

/**
  * Created by Jared on 4/17/2017.
  */
class ScootIfStmt(dt: IfStmt) extends ScootStmt(dt: Stmt) {
  def condition: ScootValue = new ScootValue(dt.getCondition())

  def target: ScootStmt = new ScootStmt(dt.getTarget())
}
