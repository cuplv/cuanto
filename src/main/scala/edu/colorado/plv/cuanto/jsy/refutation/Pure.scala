package edu.colorado.plv.cuanto.jsy.refutation

import edu.colorado.plv.cuanto.jsy._
/** Pure constraint domain
  *
  * pures \pi ::= \pi_1 /\ \pi_2 | e
  *
  * @tparam V is the abstract value type
  * @author Benno Stein
  */

case class Pure(constraints : Iterable[Expr]) extends Iterable[Expr] {
  override def iterator = constraints.iterator
}
