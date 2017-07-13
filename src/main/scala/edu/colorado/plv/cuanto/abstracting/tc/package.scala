package edu.colorado.plv.cuanto

/**
  * @author octalsrc
  */
package abstracting.tc {

  /** A domain of partially ordered values with a ''least element''
    * ([[Semilattice.bot]]) and a [[Semilattice.join]] operation
    *
    * The partial ordering in this case is [[Semilattice.implies]].
    *
    * The features of a [[Semilattice]] are commonly useful for an
    * abstract domain. */
  trait Semilattice[A] {
    /** The least element, smaller than all other elements */
    val bot: A
    def isBottom(e: A): Boolean = e == bot
    /** The partial ordering on elements in [[A]] */
    def implies(e1: A,e2: A): Boolean
    /** The least upper bound of two elements in [[A]] */
    def join(e1: A,e2: A): A
  }
  object Semilattice {
    def apply[A : Semilattice]: Semilattice[A] =
      implicitly[Semilattice[A]]

    def bot[A](implicit inst: Semilattice[A]): A = inst.bot
    def isBottom[A](e: A)(implicit inst: Semilattice[A]): Boolean = inst.isBottom(e)
    def implies[A](e1: A,e2: A)(implicit inst: Semilattice[A]): Boolean =
      inst.implies(e1,e2)
    def join[A](e1: A,e2: A)(implicit inst: Semilattice[A]): A =
      inst.join(e1,e2)
  }

  /** An extension of the [[Semilattice]] with a [[Lattice.meet]] operation and
    * a [[Lattice.top]] element that is greater than or equal to all other
    * elements */
  trait Lattice[A] extends Semilattice[A] {
    /** The greatest element, greater than or equal to all other
      * elements */
    val top: A
    def isTop(e: A): Boolean = e == top
    /** The greatest lower bound of two elements in [[A]] */
    def meet(e1: A,e2: A): A
  }
  object Lattice {
    def apply[A : Lattice]: Lattice[A] =
      implicitly[Lattice[A]]

    def top[A](implicit inst: Lattice[A]): A = inst.top
    def isTop[A](e: A)(implicit inst: Lattice[A]): Boolean = inst.isTop(e)
    def meet[A](e1: A,e2: A)(implicit inst: Lattice[A]): A = inst.meet(e1,e2)
  }

  /** Abstract an abstractable value.
    *
    * An [[Abstraction]] defines a representation function mapping a
    * single value to an abstract element.
    *
    * @tparam C is the concrete value type
    * @tparam A is the abstract element type
    */
  trait Abstraction[C,A] {
    /** Representation function. */
    def beta(c: C): A
  }
  object Abstraction {
    def apply[C,A](implicit inst: Abstraction[C,A]): Abstraction[C,A] =
      implicitly[Abstraction[C,A]]

    def beta[C,A](c: C)(implicit inst: Abstraction[C,A]): A = inst.beta(c)
  }

}
