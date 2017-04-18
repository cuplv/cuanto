package edu.colorado.plv.cuanto
package scoot.domains

/** Generic abstraction of numeric types.
  */
trait ArithDom {
  type D

  def neg(e: D): D

  def add(e1: D)(e2: D): D
  def sub(e1: D)(e2: D): D
  def mul(e1: D)(e2: D): D
  def div(e1: D)(e2: D): D
}
