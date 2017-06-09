package edu.colorado.plv.cuanto.scoot

/**
  * Created by Jared on 4/25/2017.
  */
package object ir {
  implicit def convertAddExpr(dt: soot.jimple.AddExpr) = new AddExpr(dt)

  implicit def convertAndExpr(dt: soot.jimple.AndExpr) = new AndExpr(dt)

  implicit def convertAssignStmt(dt: soot.jimple.AssignStmt) = new AssignStmt(dt)

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

  implicit def convertBody(dt: soot.Body) = new Body(dt)

  implicit def convertConditionExpr(dt: soot.jimple.ConditionExpr) : ConditionExpr = {
    if(dt.isInstanceOf[soot.jimple.EqExpr]) convertEqExpr(dt.asInstanceOf[soot.jimple.EqExpr])
    else if(dt.isInstanceOf[soot.jimple.GeExpr]) convertGeExpr(dt.asInstanceOf[soot.jimple.GeExpr])
    else if(dt.isInstanceOf[soot.jimple.GtExpr]) convertGtExpr(dt.asInstanceOf[soot.jimple.GtExpr])
    else if(dt.isInstanceOf[soot.jimple.LeExpr]) convertLeExpr(dt.asInstanceOf[soot.jimple.LeExpr])
    else if(dt.isInstanceOf[soot.jimple.LtExpr]) convertLtExpr(dt.asInstanceOf[soot.jimple.LtExpr])
    else if(dt.isInstanceOf[soot.jimple.NeExpr]) convertNeExpr(dt.asInstanceOf[soot.jimple.NeExpr])
    else throw new Exception("unsupported ConditionExpr")
  }

  implicit def convertDefinitionStmt(dt: soot.jimple.DefinitionStmt) = new DefinitionStmt(dt)

  implicit def convertDivExpr(dt: soot.jimple.DivExpr) = new DivExpr(dt)

  implicit def convertEqExpr(dt: soot.jimple.EqExpr) = new EqExpr(dt)

  implicit def convertExpr(dt: soot.jimple.Expr) : Expr = {
    if(dt.isInstanceOf[soot.jimple.BinopExpr]) convertBinopExpr(dt.asInstanceOf[soot.jimple.BinopExpr])
    else if(dt.isInstanceOf[soot.jimple.UnopExpr]) convertUnopExpr(dt.asInstanceOf[soot.jimple.UnopExpr])
    else throw new Exception("unsupported Expr")
  }

  implicit def convertGeExpr(dt: soot.jimple.GeExpr) = new GeExpr(dt)

  implicit def convertGotoStmt(dt: soot.jimple.GotoStmt) = new GotoStmt(dt)

  implicit def convertGtExpr(dt: soot.jimple.GtExpr) = new GtExpr(dt)

  implicit def convertIdentityStmt(dt: soot.jimple.IdentityStmt) = new IdentityStmt(dt)

  implicit def convertIfStmt(dt: soot.jimple.IfStmt) = new IfStmt(dt)

  implicit def convertIntConstant(dt: soot.jimple.IntConstant) = new IntConstant(dt)

  implicit def convertLeExpr(dt: soot.jimple.LeExpr) = new LeExpr(dt)

  implicit def convertLocal(dt: soot.Local) = new Local(dt)

  implicit def convertLtExpr(dt: soot.jimple.LtExpr) = new LtExpr(dt)

  implicit def convertMulExpr(dt: soot.jimple.MulExpr) = new MulExpr(dt)

  implicit def convertNeExpr(dt: soot.jimple.NeExpr) = new NeExpr(dt)

  implicit def convertNegExpr(dt: soot.jimple.NegExpr) = new NegExpr(dt)

  implicit def convertOrExpr(dt: soot.jimple.OrExpr) = new OrExpr(dt)

  implicit def convertReturnStmt(dt: soot.jimple.ReturnStmt) = new ReturnStmt(dt)

  implicit def convertStmt(dt: soot.jimple.Stmt) = new Stmt(dt)

  implicit def convertSubExpr(dt: soot.jimple.SubExpr) = new SubExpr(dt)

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

  implicit def convertXorExpr(dt: soot.jimple.XorExpr) = new XorExpr(dt)
}
