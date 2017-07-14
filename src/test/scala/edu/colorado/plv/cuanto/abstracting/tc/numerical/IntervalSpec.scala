package edu.colorado.plv.cuanto
package abstracting.tc
package numerical

import Abstraction.beta
import Lattice.meet
import Semilattice.{implies, join, isBottom, bot}

import Interval._
import Interval.implicits._

/**
  * @author octalsrc
  */
class IntervalSpec extends CuantoSpec {

  val implyTests = Table[(Interval,Interval),Boolean](
    "Does A imply B?" -> "Response",
    (Bot,Lte(4)) -> true,
    (Top,Lte(4)) -> false,
    (Gte(5),Bot) -> false,
    (Gte(5),Top) -> true,
    (Btw(0,5),Gte(2)) -> false,
    (Gte(2),Btw(3,4)) -> false,
    (Btw(0,5),Lte(10)) -> true,
    (Btw(0,10),Btw(2,8)) -> true,
    (Btw(2,8),Btw(0,10)) -> false,
    (Gte(5),Gte(0)) -> true,
    (Gte(5),Gte(10)) -> false,
    (Lte(10),Lte(8)) -> false,
    (Lte(10),Lte(12)) -> true
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
    (Gte(5),Gte(10)) -> Gte(5),
    (Lte(5),Lte(10)) -> Lte(10),
    (Gte(7),Lte(9)) -> Top,
    (Gte(9),Lte(7)) -> Top,
    (Btw(0,5),Btw(2,3)) -> Btw(0,5),
    (Btw(0,5),Btw(1,6)) -> Btw(0,6),
    (Btw(0,5),Gte(2)) -> Gte(0),
    (Btw(0,5),Lte(4)) -> Lte(5)
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
    (Gte(5),Gte(10)) -> Gte(10),
    (Lte(5),Lte(10)) -> Lte(5),
    (Gte(7),Lte(9)) -> Btw(7,9),
    (Gte(9),Lte(7)) -> Bot,
    (Btw(0,5),Btw(2,3)) -> Btw(2,3),
    (Btw(0,5),Btw(1,6)) -> Btw(1,5),
    (Btw(0,5),Gte(2)) -> Btw(2,5),
    (Btw(0,5),Lte(4)) -> Btw(0,4)
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
    (3,8) -> Btw(3,8),
    (34,-50) -> Btw(-50,34),
    (2,2) -> Btw(2,2)
  )

  val combinedMeetTests = Table[(Int,Int),Interval](
    "Concrete A, B" -> "Abstract A * B",
    (3,8) -> Bot,
    (34,-50) -> Bot,
    (2,2) -> Btw(2,2)
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
