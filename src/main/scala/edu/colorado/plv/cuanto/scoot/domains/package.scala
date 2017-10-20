package edu.colorado.plv.cuanto.scoot

package domains {
  trait Result[A <: ArithDom]
  case class Arith[A <: ArithDom](a: A) extends Result[A]
}
