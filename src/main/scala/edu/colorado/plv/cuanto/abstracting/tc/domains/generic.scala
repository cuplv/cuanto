package edu.colorado.plv.cuanto
package abstracting.tc
package domains

/** Abstraction instances for higher-order types derived from the
  * instances on their contents */
package object generic {
  object instances {

    implicit def tup2Semilattice[A: Semilattice, B: Semilattice]: Semilattice[(A,B)] =
      new Semilattice[(A,B)] {
        override val bot = (Semilattice.bot[A], Semilattice.bot[B])
        override def implies(e1: (A,B), e2: (A,B)): Boolean =
          Semilattice.implies(e1._1,e2._1) && Semilattice.implies(e1._2,e2._2)
        override def join(e1: (A,B), e2: (A,B)): (A,B) =
          (Semilattice.join(e1._1,e2._1), Semilattice.join(e1._2,e2._2))
      }

    implicit def tup2Lattice[A: Lattice, B: Lattice]: Lattice[(A,B)] =
      new Lattice[(A,B)] {
        override val bot = (Semilattice.bot[A], Semilattice.bot[B])
        override def implies(e1: (A,B), e2: (A,B)): Boolean =
          Semilattice.implies(e1._1,e2._1) && Semilattice.implies(e1._2,e2._2)
        override def join(e1: (A,B), e2: (A,B)): (A,B) =
          (Semilattice.join(e1._1,e2._1), Semilattice.join(e1._2,e2._2))

        override val top = (Lattice.top[A], Lattice.top[B])
        override def meet(e1: (A,B), e2: (A,B)): (A,B) =
          (Lattice.meet(e1._1,e2._1), Lattice.meet(e1._2,e2._2))
      }

    implicit def tup2Abstraction[C1,A1,C2,A2](
      implicit iA: Abstraction[C1,A1], iB: Abstraction[C2,A2]
    ): Abstraction[(C1,C2),(A1,A2)] =
      new Abstraction[(C1,C2),(A1,A2)] {
        override def beta(c: (C1,C2)): (A1,A2) =
          (Abstraction.beta(c._1),Abstraction.beta(c._2))
      }

  }
}
