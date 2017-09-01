package edu.colorado.plv.cuanto.jsy.numdomain

import apron.{Abstract0, Box, Interval}
import edu.colorado.plv.cuanto.jsy.Expr
import edu.colorado.plv.cuanto.jsy.arithmetic.N
import edu.colorado.plv.cuanto.jsy.arithmetic.SimpleInterpreter.{iterate, smallstep}
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

/**
  * @author Tianhan Lu
  */
class IntervalDomain extends FlatSpec with Matchers {
  /**
    *
    * val box = Array (
    *   x1 >= 1 && x1 <= 10  => new Interval(...)
    *   x2 >= 2 && x2 <= 5   => new Interval(...)
    * )
    *
    * x3 = (x1 + 3) * (x2 - 7) - 10 => new Texpr0Intern(new Texpr0BinNode(...))
    *
    * public Abstract0(man: Manager, intdim: Int, realdim: Int, c: Array[Lincons0])
    * new Abstract0(man, 0, 2, box)
    *
    *
    * x3 ϵ ?  => a0.getBound(man, texp)
    *
    * // public Abstract0(man: Manager, intdim: Int, realdim: Int, c: Array[Tcons0])
    *
    */

  val denoteTests = Table(
    "expression" -> "denotation",
    "2" -> 2,
    "1 + 1" -> 2,
    "1 - 1" -> 0,
    "1 * 1" -> 1,
    "1 / 1" -> 1,
    "1 + 2 + 3" -> 6,
    "1 + 2 * 3" -> 7
  )

  "Construct a new domain in Apron" should "not crash" in {
    val box = Array(new Interval(1, 2), new Interval(-3, 5), new Interval(3, 4, 6, 5))
    new Abstract0(new Box, 2, 1, box)

    interpreter(e => Try {
      val N(n) = iterate[Expr](e)(smallstep)
      n
    })
  }
}
