package edu.colorado.plv.cuanto.abstracting

import smtlib.theories.Core._
import smtlib.theories.Constructors._
import smtlib.parser.Terms._

package symbolic {

  trait Abstract[A] {
    val bottom: A
    def isBottom(e: A): Boolean
    def implies(e1: A, e2: A): Boolean
    def join(e1: A, e2: A): A
  }

  object Abstract {
    def apply[A : Abstract]: Abstract[A] = implicitly[Abstract[A]]
  }

}

package object symbolic {

  def postHatUp[C,A : Abstract,L](
    model: ((L => Term),(Term => Term),(L => Term)) => Option[(C,C)],
    beta: C => A,
    gammaHat: A => (L => Term),
    t: Term => Term,
    v: A
  ): A = {
    val notGammaHat = (e: A) => (l: L) => not(gammaHat(e)(l))
    def recur(low: A): A =
      model(gammaHat(v),t,notGammaHat(low)) match {
        case Some((s,sn)) =>
          recur(Abstract[A].join(low,beta(sn)))
        case _ => low
      }
    recur(Abstract[A].bottom)
  }

}
