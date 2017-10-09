package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Shawn Meier
  *         Created on 10/3/17.
  */
class Body (private val dt: soot.Body){
  def getSuccessors(stmt: Stmt): (Option[Stmt], Option[Stmt]) = {
    if (dt.getUnits.getSuccOf(stmt.getDt) != null) {
      val normalSuccessor: Option[Stmt] = Some(dt.getUnits.getSuccOf(stmt.getDt))
      (normalSuccessor, stmt match {
        case GotoStmt(target) => Some(target)
        case IfStmt(_, target) => Some(target)
        case _ => None
      })
    }else (None,None)
  }
  def getFirstNonIdentityStmt() = dt match{
    case jb : soot.jimple.JimpleBody => jb.getFirstNonIdentityStmt
  }
}

object Body {

}

