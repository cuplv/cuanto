package edu.colorado.plv.cuanto.scoot.ir

import soot.{Body, Local, PatchingChain}
import soot.jimple.Stmt
import soot.util.Chain

import scala.collection.JavaConverters._

/**
  * Created by Jared on 3/21/2017.
  */
class ScootBody(dt: Body) {
  //Todo: what is the preferred functional style for getters?
  //accessing internal data of Body should return our lifted structures, so transform in each "getter"
  lazy val stmts: List[ScootStmt] = {
    val stmtList: PatchingChain[Stmt] = dt.getUnits.asInstanceOf[PatchingChain[Stmt]]
    //transform Chain to Scala list?
    val it = stmtList.iterator()
    val retList: List[Stmt] = List()
    while(it.hasNext()) {
      it.next()::retList
    }
    //reverse b/c order is important and prepending reversed above
    //and map from Stmt to ScootStmt
    retList.reverse.map(a => new ScootStmt(a))
  }

  lazy val locals: List[ScootLocal] = {
    val localList: Chain[Local] = dt.getLocals
    //transform Chain to Scala list?
    val it = localList.iterator()
    val retList: List[Local] = List()
    while (it.hasNext()) {
      it.next() :: retList
    }
    retList.reverse.map(a => new ScootLocal(a))
  }

  //Todo: get stmts as CFG? create graph representation?
}