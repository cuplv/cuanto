package edu.colorado.plv.cuanto

/**
  * @author Bor-Yuh Evan Chang
  */
package abstracting {

  /** Abstract an abstractable value.
    *
    * An abstractable defines a representation function mapping
    * a single value to an abstract element.
    *
    * @tparam A is the abstract element type
    *
    */
  trait Abstractable[A] {
    /** Representation function. */
    implicit def represent(v: Any): A

    /** Alias `represent`. */
    @inline final implicit def beta(v: Any): A = represent(v)
  }


}
