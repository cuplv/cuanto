package edu.colorado.plv.cuanto.abstracting

import cafesat.api._
import cafesat.api.FormulaBuilder._
import cafesat.api.Formulas._


package symbolic {

  trait Logical[L,C] {
    implicit def model(l: L): Option[C]
  }

  trait Symbolic[A,L] {
    implicit def gammaHat(a: A): L
  }

  trait Logic {
    type L
  
  }

}

package object symbolic {

  import BoolVote._

  type B3 = (Boolean,Boolean,Boolean)

  val av: PropVar = propVar("a")
  val bv: PropVar = propVar("b")
  val cv: PropVar = propVar("c")

  def postHatUp(t: Formula, v: BoolVote): BoolVote = {
    def recur(low: BoolVote): BoolVote =
      modelBools(symbolicBoolVote(v) && t && (!symbolicBoolVote(low))) match {
        case Some((s,sn)) => recur(join(low,beta(sn)))
        case _ => low
      }
    recur(bottom: BoolVote)
  }

  val yayFormula: Formula = (av && bv) || (av && cv) || (bv && cv)

  def symbolicBoolVote(a: BoolVote): Formula = a match {
    case Top => true
    case Yay => yayFormula
    case Nay => !yayFormula
    case Bot => false
  }

  def modelBools(f: Formula): Option[(B3,B3)] = ???
}
