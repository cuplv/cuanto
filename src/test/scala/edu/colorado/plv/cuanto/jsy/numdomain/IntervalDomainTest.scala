package edu.colorado.plv.cuanto.jsy.numdomain

import apron._
import edu.colorado.plv.cuanto.jsy.arithmetic.{Minus, N, Plus, Times}
import edu.colorado.plv.cuanto.jsy.{Binary, Var}
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Tianhan Lu
  */
class IntervalDomainTest extends FlatSpec with Matchers {
  "Interval analysis" should "work on jsy" in {
    val manager = new Polka(false)
    val box = Array(new Interval(1, 10), new Interval(2, 5))
    val var1 = "x1"
    val var2 = "x2"
    val varnames = Array(var1, var2)
    val env = new Environment(varnames, Array[String]())
    /**
      * The element is such that vars[i] is in interval box[i].
      * All variables from vars must be exist in the environment e.
      * vars and box must have the same length.
      */
    val a1 = new Abstract1(manager, env, varnames, box)

    /**
      * (x1 + 3) * (x2 - 7) - 10
      * 1 <= x1 <= 10
      * 2 <= x2 <= 5
      */
    val target = Binary(Minus, Binary(Times, Binary(Plus, Var(var1), N(3)), Binary(Minus, Var(var2), N(7))), N(10))

    // interpret the target expression into an Apron constraint
    IntervalDomain.interpret(target) match {
      case Some(cons) =>
        val res = a1.getBound(manager, new Texpr1Intern(env, cons))
        assert(res.toString == "[-75,-18]")
      case None => throw new IllegalStateException("Cannot interpret a jsy expression into an Apron constraint")
    }
  }
}
