package edu.colorado.plv.cuanto.jsy.numerical

import edu.colorado.plv.cuanto.jsy.{boolean,arithmetic}
import scala.language.postfixOps

/** Abstract value type that can be evaluated with boolean operations.
  *
  * @tparam V is the value type
  * @author Benno Stein
  */
trait Evalable[V] extends boolean.Evalable[V] with arithmetic.Evalable[V] {

  /** Return the set of possible truth values for this value v*/
  def equ(v1: V, v2: V): V
  def neq(v1: V, v2: V): V
  def le (v1: V, v2: V): V
  def lt (v1: V, v2: V): V
  def ge (v1: V, v2: V): V
  def gt (v1: V, v2: V): V
  override def represent(v:Any) : V = throw new NotImplementedError() //Must be overriden for each value type

  class Ops(v1: V) {
    final def ===(v2: V) = equ(v1, v2)
    final def !==(v2: V) = neq(v1, v2)
    final def <= (v2: V) =  le(v1, v2)
    final def <  (v2: V) =  lt(v1, v2)
    final def >= (v2: V) =  ge(v1, v2)
    final def >  (v2: V) =  gt(v1, v2)
    override def toString = v1 toString
  }
  implicit def toNumericalOps(v1: V): Ops = new Ops(v1)
}
