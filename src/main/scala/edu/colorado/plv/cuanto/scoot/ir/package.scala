package edu.colorado.plv.cuanto.scoot

/**
  * Created by Jared on 6/13/2017.
  */
package object ir {
  implicit def convertAddExpr(dt: soot.jimple.AddExpr) : AddExpr = new AddExpr(dt)

  implicit def convertDivExpr(dt: soot.jimple.DivExpr) : DivExpr = new DivExpr(dt)

  implicit def convertIntConstant(dt: soot.jimple.IntConstant) : IntConstant = new IntConstant(dt)

  implicit def convertLocal(dt: soot.Local) : Local = new Local(dt)

  implicit def convertMulExpr(dt: soot.jimple.MulExpr) : MulExpr = new MulExpr(dt)

  implicit def convertNegExpr(dt: soot.jimple.NegExpr) : NegExpr = new NegExpr(dt)

  implicit def convertSubExpr(dt: soot.jimple.SubExpr) : SubExpr = new SubExpr(dt)

  implicit def convertValue(dt: soot.Value) : Value = {
    val s = new SootJimpleValueSwitch()
    dt.apply(s)
    s.retValue
  }
}
