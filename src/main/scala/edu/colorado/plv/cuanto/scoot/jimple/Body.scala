package edu.colorado.plv.cuanto.scoot.jimple

import scala.util.Try

/**
  * @author Shawn Meier
  *         Created on 10/3/17.
  */
class Body (private val dt: soot.Body){

  /** @return a tuple of (normal successor, jump successor) */
  def getSuccessors(stmt: Stmt): (Option[Stmt], Option[Stmt]) = {
    val normalSuccessor: Option[Stmt] = Try(convertUnit(dt.getUnits.getSuccOf(stmt.getDt))).toOption
    val jmpSuccessor: Option[Stmt] = stmt match {
      case GotoStmt(target) => Some(target)
        case IfStmt(_, target) => Some(target)
        case _ => None
      }
    (normalSuccessor, jmpSuccessor)
  }
  def getFirstNonIdentityStmt() = dt match{
    case jb : soot.jimple.JimpleBody => jb.getFirstNonIdentityStmt
  }
}

object Body {

}

