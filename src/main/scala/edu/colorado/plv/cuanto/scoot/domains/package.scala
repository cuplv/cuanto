package edu.colorado.plv.cuanto
package scoot.domains

import abstracting.{Abstractable, Abstraction}

package object domains {
  sealed trait Result
  case class Arith[A <: ArithDom](a: A) extends Result
}
