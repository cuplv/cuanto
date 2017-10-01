package edu.colorado.plv.cuanto

/**
  * @author Bor-Yuh Evan Chang
  */
package abstracting.ml {

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


}
