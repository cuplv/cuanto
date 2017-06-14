package edu.colorado.plv.cuanto.abstracting

/** 
  * @author Nicholas Lewchenko
  */
package symbolic {

}

package object symbolic {

  def postHatUp[C,A : Abstraction,L,T](
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
          recur(Abstraction[A].join(low,beta(sn)))
        }
        case _ => {
          low
        }
      }
    recur(Abstraction[A].bottom)
  }

}
