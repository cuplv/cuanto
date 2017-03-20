package edu.colorado.plv.cuanto.abstracting

/** The signature for a generic abstraction.
  *
  * An abstraction is a join semi-lattice.
  *
  * @author Bor-Yuh Evan Chang
  */
trait Abstraction {
  type Element

  val bottom: Element
  def isBottom(e: Element): Boolean

  def implies(e1: Element, e2: Element): Boolean
  def join(e1: Element, e2: Element): Element
}
