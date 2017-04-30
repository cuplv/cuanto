package edu.colorado.plv.cuanto.jsy.refinement

import edu.colorado.plv.cuanto.jsy._
import edu.colorado.plv.cuanto.jsy.objects._
/** Separation-logic abstract heap domain.
  *
  * Abstract heaps h ::= x |-> v | x.f |-> v | h_1 * h_2 | emp.
  *
  * @tparam V is the abstract value type
  * @author Benno Stein
  */

sealed trait AStore

object Emp extends AStore
object True extends AStore
case class Sep(h1: AStore, h2: AStore) extends AStore
case class HeapCell(rcvr: Var, fld: Fld, value: SymVar) extends AStore
