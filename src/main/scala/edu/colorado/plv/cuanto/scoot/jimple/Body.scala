package edu.colorado.plv.cuanto.scoot.jimple

/**
  * @author Shawn Meier
  *         Created on 10/3/17.
  */
class Body (private val dt: soot.Body){
  def getSuccessors(stmt: Stmt): Set[Stmt] = dt match{
    case s : soot.jimple.GotoStmt => ???
    case s => {
      val directSucc = dt.getUnits.getSuccOf(stmt.getDt)
      if(directSucc != null)
        Set(directSucc)
      else
        Set()
    }
  }
  def getFirstNonIdentityStmt() = dt match{
    case jb : soot.jimple.JimpleBody => jb.getFirstNonIdentityStmt
  }
}

object Body {

}

