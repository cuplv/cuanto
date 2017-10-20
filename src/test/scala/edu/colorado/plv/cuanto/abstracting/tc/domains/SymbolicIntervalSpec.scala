package edu.colorado.plv.cuanto
package abstracting.tc
package domains.interval

import smtlib.theories.Core._
import smtlib.theories.Ints._

import abstracting.tc.symbolic._

import instances._

/**
  * @author Nicholas V. Lewchenko
  */
class SymbolicIntervalSpec extends CuantoSpec {

  val gteSpec: Int => Constraint[IntSMT] =
    i => { case IntSMT(t) => GreaterThan(sTerm(t),NumeralLit(BigInt(i))) }

  val btwSpec: (Int,Int) => Constraint[IntSMT] = {
    case (g,l) => {
      case IntSMT(t) => And(
        GreaterThan(sTerm(t),NumeralLit(BigInt(g))),
        LessThan(sTerm(t),NumeralLit(BigInt(l)))
      )
    }
  }

  val modelTests = Table[Constraint[IntSMT],Int => Boolean](
    "SMT constraint" -> "Int predicate",
    gteSpec(4) -> { i => i > 4 },
    btwSpec(1,9) -> { i => i > 1 && i < 9 }
  )

  val modelInst: Model[Int] {type Schema = IntSMT} = intModel

  "The Int symbolic representation" should "produce models" in {
    forAll (modelTests) {
      (smt,p) => model("asdf",smt).map(p) should equal (Some(true))
    }

    model("asdf",btwSpec(2,1)) should equal (None)
  }


  val addSpec: Int => Transformer[IntSMT] =
    n => {
      case (IntSMT(a1),IntSMT(a2)) => Equals(
        Add(sTerm(a1),NumeralLit(BigInt(n))),
        sTerm(a2)
      )
    }

  val transformerTests = Table[
    (Transformer[IntSMT],Constraint[IntSMT],Constraint[IntSMT]),
    ((Int,Int)) => Boolean
  ](
    "SMT Transformer and constraints" -> "Int pair predicate",
    (addSpec(1),gteSpec(1),gteSpec(5)) -> { case ((i1,i2)) => (i1 + 1) == i2 }
  )

  "The Int symbolic representation" should "produce transformer models" in {
    forAll (transformerTests) {
      case ((t,pre,post),p) => modelT(t)(pre,post).map(p) should equal (Some(true))
    }
  }
}
