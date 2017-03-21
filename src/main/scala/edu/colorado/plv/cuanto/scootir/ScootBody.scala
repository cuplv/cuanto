package edu.colorado.plv.cuanto.scootir
import soot.Body
import soot.Local
import soot.PatchingChain
import soot.util.Chain
import soot.jimple.Stmt
//Todo: import soot

/**
  * Created by Jared on 3/21/2017.
  */
//Todo: import soot and type this correctly
class ScootBody(dt: Body) {
  //Todo: what is the preferred functional style for getters?
  //accessing internal data of Body should return our lifted structures, so transform in each "getter"
  def stmts: List[ScootStmt] = {
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

  def locals: List[ScootLocal] = {
    val localList: Chain[Local] = dt.getLocals
    //transform Chain to Scala list?
    val it = localList.iterator()
    val retList: List[Local] = List()
    while(it.hasNext()) {
      it.next()::retList
    }
    retList.reverse.map(a => new ScootLocal(a))
  }

  //Todo: get stmts as CFG? create graph representation?
}