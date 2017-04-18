package edu.colorado.plv.cuanto.abstracting

/** Signature for a generic abstraction.
  *
  * An abstraction is a join semi-lattice.
  *
  * @author Bor-Yuh Evan Chang
  */
trait Abstraction {
  /** The abstract element type. */
  type A

  val bottom: A
  def isBottom(e: A): Boolean = e == bottom

  def implies(e1: A, e2: A): Boolean
  def join(e1: A, e2: A): A
}
