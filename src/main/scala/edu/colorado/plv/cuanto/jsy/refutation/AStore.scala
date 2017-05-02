package edu.colorado.plv.cuanto.jsy.refutation

import edu.colorado.plv.cuanto.jsy._
import edu.colorado.plv.cuanto.jsy.objects._
/** Separation-logic abstract heap domain.
  *
  * Abstract heaps h ::= x |-> v | x.f |-> v | h_1 * h_2 | emp.
  *
  * @tparam V is the abstract value type
  * @author Benno Stein
  */

sealed trait AStore {
  def footprint: Set[(Var, Fld)]
  def wellformed: Boolean = true
}

object Emp extends AStore {
  override def footprint = Set()
}
object True extends AStore {
  override def footprint = Set()
}
case class Sep(h1: AStore, h2: AStore) extends AStore {
  override def wellformed = (h1.footprint intersect h2.footprint).isEmpty
  override def footprint = h1.footprint union h2.footprint
}
case class HeapCell(rcvr: Var, fld: Fld, value: Any) extends AStore {
  override def footprint = Set( rcvr -> fld )
}

