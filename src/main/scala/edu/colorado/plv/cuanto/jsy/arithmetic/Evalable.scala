package edu.colorado.plv.cuanto.jsy.arithmetic

import edu.colorado.plv.cuanto.abstracting.Abstractable

/** Abstract value type that can be evaluated with arithmetic operations.
  *
  * @tparam V is the value type
  * @author Bor-Yuh Evan Chang
  */
trait Evalable[V] extends Abstractable[V] {
  def negate(v1: V): V
  def plus(v1: V, v2: V): V
  def minus(v1: V, v2: V): V = plus(v1, negate(v2))
  def times(v1: V, v2: V): V
  def divide(v1: V, v2: V): V

  class Ops(v1: V) {
    final def +(v2: V) = plus(v1, v2)
    final def -(v2: V) = minus(v1, v2)
    final def *(v2: V) = times(v1, v2)
    final def /(v2: V) = divide(v1, v2)
    final def unary_- : V = negate(v1)
    override def toString = v1.toString
  }
  implicit def toArithmeticOps(v1: V): Ops = new Ops(v1)
}
