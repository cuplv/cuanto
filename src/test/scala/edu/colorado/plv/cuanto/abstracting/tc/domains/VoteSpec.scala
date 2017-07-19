package edu.colorado.plv.cuanto
package abstracting.tc
package domains

import Abstraction._
import Lattice._
import Semilattice._

import Vote._
import Vote.instances._

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

}
