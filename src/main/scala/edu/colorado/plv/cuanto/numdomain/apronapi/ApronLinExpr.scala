package edu.colorado.plv.cuanto.numdomain.apronapi

import apron.{Linexpr0, MpfrScalar, MpqScalar}
import edu.colorado.plv.cuanto.numdomain.AbsExpr
import edu.colorado.plv.cuanto.numdomain._

/**
  * Created by lumber on 4/27/17.
  */
class ApronLinExpr extends AbsExpr {
  private var Expr: Linexpr0 = _

  def expr = Expr

  override def toString: String = Expr.toString

  def this(const: Int, terms: Array[ApronLinTerm]) {
    this
    Expr = new Linexpr0(terms.map(t => t.term), new MpqScalar(const))
  }

  def this(const: Double, terms: Array[ApronLinTerm]) {
    this
    Expr = new Linexpr0(terms.map(t => t.term), new MpfrScalar(const, ROUNDING))
  }
}
