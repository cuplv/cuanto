package edu.colorado.plv.cuanto.scoot.ir

import soot.jimple.{Stmt, DefinitionStmt}

/**
  * Created by Jared on 4/17/2017.
  */
class ScootDefinitionStmt(dt: DefinitionStmt) extends ScootStmt(dt: Stmt) {
  lazy val leftOp : ScootValue = new ScootValue(dt.getLeftOp())

  lazy val rightOp : ScootValue = new ScootValue(dt.getRightOp())
}
