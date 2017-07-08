package edu.colorado.plv.cuanto.numdomain.apronapi

import apron.Interval
import edu.colorado.plv.cuanto.numdomain.AbsInterval

/**
  * Created by lumber on 4/29/17.
  */
class ApronInterval(lb: Double, ub: Double) extends AbsInterval(lb, ub) {
  def interval = new Interval(lb, ub)

  override def toString: String = interval.toString
}
