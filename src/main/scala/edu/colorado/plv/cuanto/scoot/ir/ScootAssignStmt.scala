package edu.colorado.plv.cuanto.scoot.ir

import soot.jimple.{AssignStmt, DefinitionStmt}

/**
  * Created by Jared on 4/17/2017.
  */
class ScootAssignStmt(dt: AssignStmt) extends ScootDefinitionStmt(dt: DefinitionStmt) {
  //AssignStmt has setLeftOp and setRightOp, which are unnecessary for the moment
}
