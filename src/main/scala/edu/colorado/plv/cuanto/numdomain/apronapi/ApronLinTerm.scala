package edu.colorado.plv.cuanto.numdomain.apronapi

import apron.{Linterm0, MpfrScalar, MpqScalar}
import edu.colorado.plv.cuanto.numdomain._

/**
  * Created by lumber on 4/27/17.
  */
class ApronLinTerm extends AbsTerm {
  private var Term: Linterm0 = _

  def term: Linterm0 = Term

  override def toString: String = Term.toString

  def this(coeff: Double, dim: Int) {
    this
    Term = new Linterm0(dim, new MpfrScalar(coeff, ROUNDING))
  }

  def this(coeff: Int, dim: Int) {
    this
    Term = new Linterm0(dim, new MpqScalar(coeff))
  }

  def this(lb: Double, ub: Double, dim: Int) {
    this
    Term = new Linterm0(dim, new ApronInterval(lb, ub).interval)
  }
}
