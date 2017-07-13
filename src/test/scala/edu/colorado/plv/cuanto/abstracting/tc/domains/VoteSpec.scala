package edu.colorado.plv.cuanto
package abstracting.tc
package domains

import Abstraction.beta
import Semilattice.{implies, join}

import Vote.implicits._

/**
  * @author octalsrc
  */
class VoteSpec extends CuantoSpec {

  val implyExamples = Table[Vote,Vote](
    "A" -> "B",
    Bot -> Yay,
    Bot -> Top,
    Bot -> Bot,
    Top -> Top,
    Yay -> Yay,
    Nay -> Nay
  )

  val implyFailures = Table[Vote,Vote](
    "A" -> "B",
    Top -> Yay,
    Nay -> Yay,
    Yay -> Nay,
    Yay -> Bot
  )

  "The Vote abstraction" should "imply" in {
    forAll (implyExamples) {
      (a,b) => implies(a,b) should equal (true)
    }
    forAll (implyFailures) {
      (a,b) => implies(a,b) should equal (false)
    }
  }

  val joinTests = Table[(Vote,Vote),Vote](
    "(A , B)" -> "(A + B)",
    (Bot,Top) -> Top,
    (Bot,Yay) -> Yay,
    (Yay,Yay) -> Yay,
    (Yay,Nay) -> Top,
    (Top,Nay) -> Top
  )

  it should "join" in {
    forAll (joinTests) {
      (ab,r) => ab match {
        case (a,b) => join(a,b) should equal (r)
      }
    }
  }

  val betaTests1 = Table[Boolean,Vote](
    "Concrete" -> "Abstract",
    true -> Yay,
    false -> Nay
  )

  val betaTests3 = Table[(Boolean,Boolean,Boolean),Vote](
    "Concrete" -> "Abstract",
    (true,true,true) -> Yay,
    (false,true,true) -> Yay,
    (true,false,false) -> Nay,
    (false,false,false) -> Nay,
    (false,false,true) -> Nay
  )

  val betaTestsList = Table[List[Boolean],Vote](
    "Concrete" -> "Abstract",
    List(true) -> Yay,
    List() -> Top,
    List(true,false) -> Top,
    List(true,true,false) -> Yay,
    List(false,false,false,false,true,true,true) -> Nay
  )

  it should "abstract a single Boolean" in {
    forAll (betaTests1) {
      (c,a) => beta(c) should equal (a)
    }
  }

  it should "abstract three Booleans" in {
    forAll (betaTests3) {
      (c,a) => beta(c) should equal (a)
    }
  }

  it should "abstract a list of Booleans" in {
    forAll (betaTestsList) {
      (c,a) => beta(c) should equal (a)
    }
  }

}
