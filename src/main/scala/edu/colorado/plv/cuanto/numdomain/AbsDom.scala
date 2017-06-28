package edu.colorado.plv.cuanto.numdomain

/**
  * Created by lumber on 4/29/17.
  */
abstract class AbsDom {
  def getBound(expr: AbsExpr): AbsInterval
  def getBound(dim: Int): AbsInterval
  def satisfy(cons: AbsCons): Boolean
  def join(absDom: AbsDom): Boolean
}
