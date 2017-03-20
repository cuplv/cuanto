package edu.colorado.plv.cuanto.jsy
package arithmetic

import edu.colorado.plv.cuanto.recursing.FixFun

/* Try different ways of modularizing interpreters */

/** An ML-style signature: values are abstract type. */
trait EvalableM {
  /** An abstraction of values. */
  type V

  def negate(v1: V): V
  def plus(v1: V, v2: V): V
  def minus(v1: V, v2: V): V = plus(v1, negate(v2))
  def times(v1: V, v2: V): V
  def divide(v1: V, v2: V): V
  implicit def represent(d: Double): V

  class Ops(v1: V) {
    def +(v2: V) = plus(v1, v2)
    def -(v2: V) = minus(v1, v2)
    def *(v2: V) = times(v1, v2)
    def /(v2: V) = divide(v1, v2)
    def unary_- : V = negate(v1)
    override def toString = v1.toString
  }
  implicit def toOps(v1: V): Ops = new Ops(v1)
}

trait InterpreterM {
  type V
  def denote: FixFun[Expr, V]
}

object InterpreterM {
  /** ML-style functor from a [[EvalableM]] module to a
    * [[InterpreterM]] module.
    *
    * @param eval
    * @return
    */
  def Make(eval: EvalableM): InterpreterM { type V = eval.V } = new InterpreterM {
    type V = eval.V
    import eval._

    def denote: FixFun[Expr, V] = {
      FixFun(rec => {
        case N(n) => n
        case Unary(Neg, e1) => - rec(e1)
        case Binary(Plus, e1, e2) => rec(e1) + rec(e2)
        case Binary(Minus, e1, e2) => rec(e1) - rec(e2)
        case Binary(Times, e1, e2) => rec(e1) * rec(e2)
        case Binary(Div, e1, e2) => rec(e1) / rec(e2)
      })
    }
  }

  type ConcreteInterpreter = InterpreterM { type V = Double }

  val Concrete: ConcreteInterpreter = InterpreterM.Make(new EvalableM {
    type V = Double
    override def negate(v1: Double) = - v1
    override def plus(v1: Double, v2: Double) = v1 + v2
    override def minus(v1: Double, v2: Double) = v1 - v2
    override def times(v1: Double, v2: Double) = v1 * v2
    override def divide(v1: Double, v2: Double) = v1 / v2
    override def represent(d: Double) = d
  })
}


/** A Haskell-style typeclass */
trait EvalableT[V] {
  def negate(v1: V): V
  def plus(v1: V, v2: V): V
  def minus(v1: V, v2: V): V = plus(v1, negate(v2))
  def times(v1: V, v2: V): V
  def divide(v1: V, v2: V): V
  implicit def represent(d: Double): V

  class Ops(v1: V) {
    def +(v2: V) = plus(v1, v2)
    def -(v2: V) = minus(v1, v2)
    def *(v2: V) = times(v1, v2)
    def /(v2: V) = divide(v1, v2)
    def unary_- : V = negate(v1)
    override def toString = v1.toString
  }
  implicit def toOps(v1: V): Ops = new Ops(v1)
}

object InterpreterT {
  def denote[V](implicit eval: EvalableT[V]): FixFun[Expr, V] = {
    import eval._
    FixFun(rec => {
      case N(n) => n
      case Unary(Neg, e1) => - rec(e1)
      case Binary(Plus, e1, e2) => rec(e1) + rec(e2)
      case Binary(Minus, e1, e2) => rec(e1) - rec(e2)
      case Binary(Times, e1, e2) => rec(e1) * rec(e2)
      case Binary(Div, e1, e2) => rec(e1) / rec(e2)
    })
  }
}

object ConcreteInterpreterT {
  /* could also use an object */
  val eval = new EvalableT[Double] {
    override def negate(v1: Double) = - v1
    override def plus(v1: Double, v2: Double) = v1 + v2
    override def minus(v1: Double, v2: Double) = v1 - v2
    override def times(v1: Double, v2: Double) = v1 * v2
    override def divide(v1: Double, v2: Double) = v1 / v2
    override def represent(d: Double) = d
  }
}