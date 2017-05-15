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

  type B3P = (Boolean,Boolean,Boolean)

  val av: PropVar = propVar("a")
  val bv: PropVar = propVar("b")
  val cv: PropVar = propVar("c")
  val av2: PropVar = propVar("a2")
  val bv2: PropVar = propVar("b2")
  val cv2: PropVar = propVar("c2")

  def postHatUp(t: (B3,B3) => Formula, v: BoolVote): BoolVote = {
    def recur(low: BoolVote): BoolVote =
      modelBools(symbolicBoolVote(v,(av,bv,cv)) && t((av,bv,cv),(av2,bv2,cv2)) && (!symbolicBoolVote(low,(av2,bv2,cv2)))) match {
        case Some((s,sn)) => recur(join(low,beta(sn)))
        case _ => low
      }
    recur(bottom: BoolVote)
  }

  val yayFormula: B3 => Formula =
    {case (a,b,c) => (a && b) || (a && c) || (b && c) }

  def symbolicBoolVote(a: BoolVote, bs: B3): Formula = a match {
    case Top => true
    case Yay => yayFormula(bs)
    case Nay => !yayFormula(bs)
    case Bot => false
  }

  def modelBools(f: Formula): Option[(B3P,B3P)] = {
    Solver.solveForSatisfiability(f) match {
      case None => None
      case Some(model) => {
        Some((model(av),model(bv),model(cv)),(model(av2),model(bv2),model(cv2)))
      }
    }
  }
}
