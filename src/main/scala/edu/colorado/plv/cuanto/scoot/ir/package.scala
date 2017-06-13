package edu.colorado.plv.cuanto.scoot

/**
  * Created by Jared on 6/13/2017.
  */
package object ir {
  implicit def convertBinopExpr(dt: soot.jimple.BinopExpr) : BinopExpr = {
    if(dt.isInstanceOf[soot.jimple.AddExpr]) convertAddExpr(dt.asInstanceOf[soot.jimple.AddExpr])
    else if(dt.isInstanceOf[soot.jimple.AndExpr]) convertAndExpr(dt.asInstanceOf[soot.jimple.AndExpr])
    else if(dt.isInstanceOf[soot.jimple.DivExpr]) convertDivExpr(dt.asInstanceOf[soot.jimple.DivExpr])
    else if(dt.isInstanceOf[soot.jimple.MulExpr]) convertMulExpr(dt.asInstanceOf[soot.jimple.MulExpr])
    else if(dt.isInstanceOf[soot.jimple.OrExpr]) convertOrExpr(dt.asInstanceOf[soot.jimple.OrExpr])
    else if(dt.isInstanceOf[soot.jimple.SubExpr]) convertSubExpr(dt.asInstanceOf[soot.jimple.SubExpr])
    else if(dt.isInstanceOf[soot.jimple.XorExpr]) convertXorExpr(dt.asInstanceOf[soot.jimple.XorExpr])
    else throw new Exception("unsupported BinopExpr")
  }

  implicit def convertExpr(dt: soot.jimple.Expr) : Expr = {
    if(dt.isInstanceOf[soot.jimple.BinopExpr]) convertBinopExpr(dt.asInstanceOf[soot.jimple.BinopExpr])
    else if(dt.isInstanceOf[soot.jimple.UnopExpr]) convertUnopExpr(dt.asInstanceOf[soot.jimple.UnopExpr])
    else throw new Exception("unsupported Expr")
  }

  implicit def convertIntConstant(dt: soot.jimple.IntConstant) = new IntConstant(dt)

  implicit def convertLocal(dt: soot.Local) = new Local(dt)

  implicit def convertNegExpr(dt: soot.jimple.NegExpr) = new NegExpr(dt)

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
