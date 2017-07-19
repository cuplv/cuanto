package edu.colorado.plv.cuanto
package abstracting.tc
package domains

import Abstraction._
import Lattice._
import Semilattice._

import generic.instances._

import Interval._
import Interval.instances._

import Vote._
import Vote.instances._

/**
  * @author Nicholas V. Lewchenko
  */
class GenericAbstractionsSpec extends CuantoSpec {

  "A 2-tuple of abstractions" should "join" in {
    join((bot[Interval],yay),(btw(1,2),nay)) should equal ((btw(1,2),top[Vote]))
  }

  it should "imply" in {
    implies((bot[Interval],yay),(btw(1,2),nay)) should equal (false)
    implies((bot[Interval],yay),(bot[Interval],top[Vote])) should equal (true)
  }

  it should "meet" in {
    meet((bot[Interval],yay),(btw(1,2),nay)) should equal ((bot[Interval],bot[Vote]))
  }

  it should "abstract" in {
    beta((true, false)) should equal ((yay,nay))
    beta((4, List(true,false))) should equal ((btw(4,4),top[Vote]))
  }

  it should "get bot and top right" in {
    bot[(Interval,Interval)] should equal ((bot[Interval],bot[Interval]))
    top[(Vote,Interval)] should equal ((top[Vote],top[Interval]))
    isBottom((bot[Vote],bot[Interval])) should equal (true)

    // Works with type annotation
    isTop[(Vote,Interval)]((top[Vote],top[Interval])) should equal (true)
    // and without type annotation
    isTop((top[Vote],top[Interval])) should equal (true)
  }
}
