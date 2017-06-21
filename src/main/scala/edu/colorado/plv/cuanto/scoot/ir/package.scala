package edu.colorado.plv.cuanto.scoot

/**
  * Created by Jared on 6/13/2017.
  */
package object ir {
  implicit def convertAddExpr(dt: soot.jimple.AddExpr) : AddExpr = new AddExpr(dt)

  implicit def convertBinopExpr(dt: soot.jimple.BinopExpr) : BinopExpr = {
    if(dt.isInstanceOf[soot.jimple.AddExpr]) convertAddExpr(dt.asInstanceOf[soot.jimple.AddExpr])
    else if(dt.isInstanceOf[soot.jimple.DivExpr]) convertDivExpr(dt.asInstanceOf[soot.jimple.DivExpr])
    else if(dt.isInstanceOf[soot.jimple.MulExpr]) convertMulExpr(dt.asInstanceOf[soot.jimple.MulExpr])
    else if(dt.isInstanceOf[soot.jimple.SubExpr]) convertSubExpr(dt.asInstanceOf[soot.jimple.SubExpr])
    else throw new Exception("unsupported BinopExpr")
  }

  implicit def convertDivExpr(dt: soot.jimple.DivExpr) : DivExpr = new DivExpr(dt)

  implicit def convertExpr(dt: soot.jimple.Expr) : Expr = {
    if(dt.isInstanceOf[soot.jimple.BinopExpr]) convertBinopExpr(dt.asInstanceOf[soot.jimple.BinopExpr])
    else if(dt.isInstanceOf[soot.jimple.UnopExpr]) convertUnopExpr(dt.asInstanceOf[soot.jimple.UnopExpr])
    else throw new Exception("unsupported Expr")
  }

  implicit def convertIntConstant(dt: soot.jimple.IntConstant) : IntConstant = new IntConstant(dt)

  implicit def convertLocal(dt: soot.Local) : Local = new Local(dt)

  implicit def convertMulExpr(dt: soot.jimple.MulExpr) : MulExpr = new MulExpr(dt)

  implicit def convertNegExpr(dt: soot.jimple.NegExpr) : NegExpr = new NegExpr(dt)

  implicit def convertSubExpr(dt: soot.jimple.SubExpr) : SubExpr = new SubExpr(dt)

  implicit def convertUnopExpr(dt: soot.jimple.UnopExpr) : UnopExpr = {
    if(dt.isInstanceOf[soot.jimple.NegExpr]) convertNegExpr(dt.asInstanceOf[soot.jimple.NegExpr])
    else throw new Exception("unsupported UnopExpr")
  }

  implicit def convertValue(dt: soot.Value) : Value = {
    if(dt.isInstanceOf[soot.jimple.Expr]) convertExpr(dt.asInstanceOf[soot.jimple.Expr])
    else if(dt.isInstanceOf[soot.Local]) convertLocal(dt.asInstanceOf[soot.Local])
    else if (dt.isInstanceOf[soot.jimple.IntConstant]) convertIntConstant(dt.asInstanceOf[soot.jimple.IntConstant])
    else throw new Exception("unsupported Value converted")
  }
}
