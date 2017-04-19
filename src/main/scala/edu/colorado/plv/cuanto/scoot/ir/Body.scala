package edu.colorado.plv.cuanto.scoot.ir

import soot.PatchingChain
import soot.util.Chain

/**
  * Created by Jared on 3/21/2017.
  */
class Body(dt: soot.Body) {
  //Todo: what is the preferred functional style for getters?
  //accessing internal data of Body should return our lifted structures, so transform in each "getter"
  lazy val stmts: List[Stmt] = {
    val stmtList: PatchingChain[soot.jimple.Stmt] = dt.getUnits.asInstanceOf[PatchingChain[soot.jimple.Stmt]]
    //transform Chain to Scala list?
    val it = stmtList.iterator()
    val retList: List[soot.jimple.Stmt] = List()
    while(it.hasNext()) {
      it.next()::retList
    }
    //reverse b/c order is important and prepending reversed above
    //and map from Stmt to Stmt
    retList.reverse.map(a => new Stmt(a))
  }

  lazy val locals: List[Local] = {
    val localList: Chain[soot.Local] = dt.getLocals
    //transform Chain to Scala list?
    val it = localList.iterator()
    val retList: List[soot.Local] = List()
    while (it.hasNext()) {
      it.next() :: retList
    }
    retList.reverse.map(a => new Local(a))
  }
}