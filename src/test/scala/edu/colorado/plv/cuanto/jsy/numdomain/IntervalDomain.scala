package edu.colorado.plv.cuanto.jsy.numdomain

import apron._
import edu.colorado.plv.cuanto.jsy.arithmetic.{Minus, N, Plus, Times}
import edu.colorado.plv.cuanto.jsy.{Binary, Var}
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.HashMap

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
    val manager = new Polka(false)
    val box = Array(new Interval(1, 10), new Interval(2, 5))
    val varnames = Array("x1", "x2")
    val env = new Environment(varnames, Array[String]())
    /**
      * The element is such that vars[i] is in interval box[i].
      * All variables from vars must be exist in the environment e.
      * vars and box must have the same length.
      */
    val a1 = new Abstract1(manager, env, varnames, box)

    val target = Binary(Minus, Binary(Times, Binary(Plus, Var("x1"), N(3)), Binary(Minus, Var("x2"), N(7))), N(10)) // (x1 + 3) * (x2 - 7) - 10

    // interpret the target expression into an Apron constraint
    val cons = IntervalDomain.interpret(target)
    cons match {
      case Some(cons) => a1.getBound(manager, new Texpr1Intern(env, cons))
    }
  }
}
