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

  def postHatUp[C,A : Abstract,L,T](
    model: ((L => T),((L,L) => T),(L => T)) => Option[(C,C)],
    beta: C => A,
    gammaHat: A => (L => T),
    t: (L,L) => T,
    neg: T => T,
    v: A
  ): A = {
    val notGammaHat = (e: A) => (l: L) => neg(gammaHat(e)(l))
    def recur(low: A): A =
      model(gammaHat(v),t,notGammaHat(low)) match {
        case Some((s,sn)) => {
          println("found something")
          println(s)
          println(sn)
          println(low)
          println(beta(sn))
          println(Abstract[A].join(low,beta(sn)))
          recur(Abstract[A].join(low,beta(sn)))
        }
        case _ => {
          println("did not find something")
          low
        }
      }
    recur(Abstract[A].bottom)
  }

}
