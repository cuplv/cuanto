package edu.colorado.plv.cuanto.scoot.ir

import soot.jimple.{Expr, UnopExpr}

/**
  * Created by Jared on 4/17/2017.
  */
class ScootUnopExpr(dt: UnopExpr) extends ScootExpr(dt: Expr) {
  def op: ScootValue = new ScootValue(dt.getOp())
}
