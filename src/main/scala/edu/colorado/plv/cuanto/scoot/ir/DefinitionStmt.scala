package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class DefinitionStmt(dt: soot.jimple.DefinitionStmt) extends Stmt(dt: soot.jimple.Stmt) {
  lazy val leftOp : Value = new Value(dt.getLeftOp())

  lazy val rightOp : Value = new Value(dt.getRightOp())
}

object DefinitionStmt {
  def unapply(s: DefinitionStmt): Option[(Value, Value)] =
    Some((s.leftOp, s.rightOp))
}