package edu.colorado.plv.cuanto
package abstracting.tc
package domains.vote

import Abstraction._
import Lattice._
import Semilattice._

import instances._

/**
  * @author Nicholas V. Lewchenko
  */
class VoteSpec extends CuantoSpec {

  val implyExamples = Table[Vote,Vote](
    "A" -> "B",
    bot -> yay,
    bot -> top,
    bot -> bot,
    top -> top,
    yay -> yay,
    nay -> nay
  )

  val implyFailures = Table[Vote,Vote](
    "A" -> "B",
    top -> yay,
    nay -> yay,
    yay -> nay,
    yay -> bot
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
    (bot,top) -> top,
    (bot,yay) -> yay,
    (yay,yay) -> yay,
    (yay,nay) -> top,
    (top,nay) -> top
  )

  it should "join" in {
    forAll (joinTests) {
      (ab,r) => ab match {
        case (a,b) => join(a,b) should equal (r)
      }
    }
  }

  val meetTests = Table[(Vote,Vote),Vote](
    "(A , B)" -> "(A + B)",
    (bot,top) -> bot,
    (bot,yay) -> bot,
    (yay,yay) -> yay,
    (yay,nay) -> bot,
    (top,nay) -> nay
  )

  it should "meet" in {
    forAll (meetTests) {
      (ab,r) => ab match {
        case (a,b) => meet(a,b) should equal (r)
      }
    }
  }

  val betaTests1 = Table[Boolean,Vote](
    "Concrete" -> "Abstract",
    true -> yay,
    false -> nay
  )

  val betaTests3 = Table[(Boolean,Boolean,Boolean),Vote](
    "Concrete" -> "Abstract",
    (true,true,true) -> yay,
    (false,true,true) -> yay,
    (true,false,false) -> nay,
    (false,false,false) -> nay,
    (false,false,true) -> nay
  )

  val betaTestsList = Table[List[Boolean],Vote](
    "Concrete" -> "Abstract",
    List(true) -> yay,
    List() -> top,
    List(true,false) -> top,
    List(true,true,false) -> yay,
    List(false,false,false,false,true,true,true) -> nay
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

  it should "detect bottom" in {
    isBottom(bot[Vote]) should equal (true)
  }
  it should "be able to distinguish top as not bottom" in {
    isBottom(top[Vote]) should equal (false)
  }
  it should "distinguish an interval from bottom" in {
    isBottom(yay) should equal (false)
  }
  it should "distinguish the result of beta from bottom" in {
    isBottom(beta(true)) should equal (false)
  }

  it should "detect top" in {
    isTop(top[Vote]) should equal (true)
  }
  it should "be able to distinguish bottom as not top" in {
    isTop(bot[Vote]) should equal (false)
  }
  it should "distinguish an interval from top" in {
    isTop(yay) should equal (false)
  }
  it should "distinguish the result of beta from top" in {
    isTop(beta(true)) should equal (false)
  }
}
