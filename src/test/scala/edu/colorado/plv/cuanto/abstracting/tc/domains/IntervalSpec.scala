package edu.colorado.plv.cuanto
package abstracting.tc
package domains

import Abstraction.beta
import Lattice.{meet, top}
import Semilattice.{bot, implies, join, isBottom}

import Interval._
import Interval.instances._

/**
  * @author octalsrc
  */
class IntervalSpec extends CuantoSpec {

  val implyTests = Table[(Interval,Interval),Boolean](
    "Does A imply B?" -> "Response",
    (bot,lte(4)) -> true,
    (top,lte(4)) -> false,
    (gte(5),bot) -> false,
    (gte(5),top) -> true,
    (btw(0,5),gte(2)) -> false,
    (gte(2),btw(3,4)) -> false,
    (btw(0,5),lte(10)) -> true,
    (btw(0,10),btw(2,8)) -> true,
    (btw(2,8),btw(0,10)) -> false,
    (gte(5),gte(0)) -> true,
    (gte(5),gte(10)) -> false,
    (lte(10),lte(8)) -> false,
    (lte(10),lte(12)) -> true
  )

  "The Interval abstraction" should "imply" in {
    forAll (implyTests) {
      (ab,r) => ab match {
        case (a,b) => implies(a,b) should equal (r)
      }
    }
  }

  val joinTests = Table[(Interval,Interval),Interval](
    "(A , B)" -> "(A + B)",
    (gte(5),gte(10)) -> gte(5),
    (lte(5),lte(10)) -> lte(10),
    (gte(7),lte(9)) -> top,
    (gte(9),lte(7)) -> top,
    (btw(0,5),btw(2,3)) -> btw(0,5),
    (btw(0,5),btw(1,6)) -> btw(0,6),
    (btw(0,5),gte(2)) -> gte(0),
    (btw(0,5),lte(4)) -> lte(5)
  )

  it should "join" in {
    forAll (joinTests) {
      (ab,r) => ab match {
        case (a,b) => join(a,b) should equal (r)
      }
    }
  }

  val meetTests = Table[(Interval,Interval),Interval](
    "(A , B)" -> "(A * B)",
    (gte(5),gte(10)) -> gte(10),
    (lte(5),lte(10)) -> lte(5),
    (gte(7),lte(9)) -> btw(7,9),
    (gte(9),lte(7)) -> bot,
    (btw(0,5),btw(2,3)) -> btw(2,3),
    (btw(0,5),btw(1,6)) -> btw(1,5),
    (btw(0,5),gte(2)) -> btw(2,5),
    (btw(0,5),lte(4)) -> btw(0,4)
  )

  it should "meet" in {
    forAll (meetTests) {
      (ab,r) => ab match {
        case (a,b) => meet(a,b) should equal (r)
      }
    }
  }

  def checkrep[C,A](
    c: C,
    a: A
  )(
    implicit inst: Abstraction[C,A]
  ): Boolean =
    beta(c) == a

  def checkjoin[C,A : Semilattice](
    c1: C, c2: C
  )(
    implicit inst: Abstraction[C,A]
  ): A = {
    join(beta(c1),beta(c2))
  }

  def checkmeet[C,A : Lattice](
    c1: C, c2: C
  )(
    implicit inst: Abstraction[C,A]
  ): A = {
    meet(beta(c1),beta(c2))
  }

//  def checkbot[C](c : C)(implicit inst: Semilattice[C]): Boolean ={
//    isBottom(c)
//  }

  val betaTests = Table[Int,Interval](
    "Concrete" -> "Abstract",
    5 -> btw(5,5),
    -3 -> btw(-3,-3)
  )

  val combinedJoinTests = Table[(Int,Int),Interval](
    "Concrete A, B" -> "Abstract A + B",
    (3,8) -> btw(3,8),
    (34,-50) -> btw(-50,34),
    (2,2) -> btw(2,2)
  )

  val combinedMeetTests = Table[(Int,Int),Interval](
    "Concrete A, B" -> "Abstract A * B",
    (3,8) -> bot,
    (34,-50) -> bot,
    (2,2) -> btw(2,2)
  )

  it should "abstract Ints" in {
    forAll (betaTests) {
      (c,a) => checkrep(c,a) should equal (true)
    }
    forAll (combinedJoinTests) {
      (cs,a) => (cs match {
        case (c1,c2) => checkjoin(c1,c2)
      }) should equal (a)
    }
    forAll (combinedMeetTests) {
      (cs,a) => (cs match {
        case (c1,c2) => checkmeet(c1,c2)
      }) should equal (a)
    }
  }
  it should "detect bottom" in {
    isBottom(bot[Interval]) should equal (true)
    isBottom(btw(2,3)) should equal (false)
    isBottom(beta(3)) should equal (false)

  }

}
