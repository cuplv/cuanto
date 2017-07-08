package edu.colorado.plv.cuanto.numdomain.apronapi

import apron.Texpr0Intern
import edu.colorado.plv.cuanto.numdomain.AbsExpr

/**
  * Created by lumber on 4/27/17.
  */
class ApronNonLinExpr extends AbsExpr {
  private var Expr: Texpr0Intern = _

  def expr = Expr

  override def toString: String = expr.toString

  def this(expr: ApronLinExpr) {
    this
    Expr = new Texpr0Intern(expr.expr)
  }

  def this(term: ApronNonLinTerm) {
    this
    Expr = new Texpr0Intern(term.term)
  }
}
