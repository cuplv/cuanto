package edu.colorado.plv.cuanto
package scoot.domains

import abstracting._

/** Concrete domain of integer values.
  */
case class IntDom(i: Int) extends ArithDom
    with Abstractable[Int,IntDom] {
  override type D = IntDom

  override def represent(i: Int): IntDom = IntDom(i)

  override def neg(e: IntDom): IntDom = e match {
    case IntDom(i) => i * -1
  }

  override def add(e1: IntDom)(e2: IntDom): IntDom =
    (e1,e2) match {
      case (IntDom(i1),IntDom(i2)) => i1 + i2
    }
  override def sub(e1: IntDom)(e2: IntDom): IntDom =
    (e1,e2) match {
      case (IntDom(i1),IntDom(i2)) => i1 - i2
    }    
  override def mul(e1: IntDom)(e2: IntDom): IntDom =
    (e1,e2) match {
      case (IntDom(i1),IntDom(i2)) => i1 * i2
    }
  override def div(e1: IntDom)(e2: IntDom): IntDom =
    (e1,e2) match {
      case (IntDom(i1),IntDom(i2)) => i1 / i2
    }
}
