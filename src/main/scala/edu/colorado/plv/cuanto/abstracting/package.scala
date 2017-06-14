package edu.colorado.plv.cuanto

/**
  * @author Bor-Yuh Evan Chang
  * @author Nicholas Lewchenko
  */
package abstracting {

  /** Abstract an abstractable value.
    *
    * An abstractable defines a representation function mapping
    * a single value to an abstract element.
    *
    * @tparam C is the concrete value type
    * @tparam A is the abstract element type
    *
    */
  trait Abstractable[C,A] {
    /** Representation function. */
    implicit def represent(c: C): A

    /** Alias `represent`. */
    @inline final implicit def beta(c: C): A = represent(c)
  }

  trait Abstraction[A] {
    val bottom: A
    def isBottom(e: A): Boolean
    def implies(e1: A, e2: A): Boolean
    def join(e1: A, e2: A): A
  }

  object Abstraction {
    def apply[A : Abstraction]: Abstraction[A] =
      implicitly[Abstraction[A]]
  }



}
