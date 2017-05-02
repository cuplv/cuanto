package edu.colorado.plv.cuanto.scoot

/**
  * Created by Jared on 4/25/2017.
  */
package object ir {
  implicit def convertAddExpr(dt: soot.jimple.AddExpr) = new AddExpr(dt);

  implicit def convertAndExpr(dt: soot.jimple.AndExpr) = new AndExpr(dt);

  implicit def convertAssignStmt(dt: soot.jimple.AssignStmt) = new AssignStmt(dt);

  implicit def convertBinopExpr(dt: soot.jimple.BinopExpr) = new BinopExpr(dt);

  implicit def convertBody(dt: soot.Body) = new Body(dt);

  implicit def convertConditionExpr(dt: soot.jimple.ConditionExpr) = new ConditionExpr(dt);

  implicit def convertDefinitionStmt(dt: soot.jimple.DefinitionStmt) = new DefinitionStmt(dt);

  implicit def convertDivExpr(dt: soot.jimple.DivExpr) = new DivExpr(dt);

  implicit def convertEqExpr(dt: soot.jimple.EqExpr) = new EqExpr(dt);

  implicit def convertExpr(dt: soot.jimple.Expr) = new Expr(dt);

  implicit def convertGotoStmt(dt: soot.jimple.GotoStmt) = new GotoStmt(dt);

  implicit def convertGtExpr(dt: soot.jimple.GtExpr) = new GtExpr(dt);

  implicit def convertIdentityStmt(dt: soot.jimple.IdentityStmt) = new IdentityStmt(dt);

  implicit def convertIfStmt(dt: soot.jimple.IfStmt) = new IfStmt(dt);

  implicit def convertLeExpr(dt: soot.jimple.LeExpr) = new LeExpr(dt);

  implicit def convertLocal(dt: soot.Local) = new Local(dt);

  implicit def convertLtExpr(dt: soot.jimple.LtExpr) = new LtExpr(dt);

  implicit def convertMulExpr(dt: soot.jimple.MulExpr) = new MulExpr(dt);

  implicit def convertNeExpr(dt: soot.jimple.NeExpr) = new NeExpr(dt);

  implicit def convertNegExpr(dt: soot.jimple.NegExpr) = new NegExpr(dt);

  implicit def convertOrExpr(dt: soot.jimple.OrExpr) = new OrExpr(dt);

  implicit def convertReturnStmt(dt: soot.jimple.ReturnStmt) = new ReturnStmt(dt);

  implicit def convertStmt(dt: soot.jimple.Stmt) = new Stmt(dt);

  implicit def convertSubExpr(dt: soot.jimple.SubExpr) = new SubExpr(dt);

  implicit def convertUnopExpr(dt: soot.jimple.UnopExpr) = new UnopExpr(dt);

  implicit def convertValue(dt: soot.Value) = new Value(dt);

  implicit def convertXorExpr(dt: soot.jimple.XorExpr) = new XorExpr(dt);
}
