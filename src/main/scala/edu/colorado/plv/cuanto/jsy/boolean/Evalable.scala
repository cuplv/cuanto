package edu.colorado.plv.cuanto.jsy.boolean

import edu.colorado.plv.cuanto.abstracting.Abstractable
import scala.language.postfixOps

/** Abstract value type that can be evaluated with boolean operations.
  *
  * @tparam V is the value type
  * @author Benno Stein
  */
trait Evalable[V] extends Abstractable[V] {

  /** Return the set of possible truth values for this value v*/
  def truthiness(v: V): Set[Boolean]

  def not(v: V): V
  def and(v1: V, v2: V): V
  def or (v1: V, v2: V): V
  def ite(cond: V, true_branch: V, false_branch: V): V

  class Ops(v1: V) {
    final def &&(v2: V) =   and(v1, v2)
    final def ||(v2: V) =   or (v1, v2)
    final def unary_! : V = not(v1)
    override def toString = v1 toString
  }
  implicit def toBooleanOps(v1: V): Ops = new Ops(v1)
}
