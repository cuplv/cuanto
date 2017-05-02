package edu.colorado.plv.cuanto.jsy.refutation

import edu.colorado.plv.cuanto.jsy._
import scala.util.Try

/** Separation-logic abstract heap domain.
  *
  * Abstract heaps h ::= x |-> v | x.f |-> v | h_1 * h_2 | emp.
  *
  * @tparam V is the abstract value type
  * @author Benno Stein
  */

class AState(val h: AStore, val p: Pure) {
  override def toString():String = s"AState($h, $p)"
  /** backwards transfer function for an assignment `oldV := newV` */
  def write(oldV: Var, newV: Expr): Option[AState] = {
    def subH: AStore => (AStore, Iterable[Expr])  = {
      case Emp => (Emp, Nil)
      case True => (True, Nil)
      case Sep(h1,h2) =>
        val (h1_prime, h1_pures) = subH(h1)
        val (h2_prime, h2_pures) = subH(h1)
        ((h1_prime, h2_prime) match {
          case (Emp,_) => h2_prime
          case (_,Emp) => h1_prime
          case (True,_) | (_,True) => True
          case _ => Sep(h1_prime,h2_prime)
        }, (h1_pures ++ h2_pures))
      case HeapCell(rcvr, fld, value) => ???
    }

    def subP: Expr => Expr = {
      case Binary(op,l,r) => Binary(op, subP(l), subP(r))
      case Unary(op,e) => Unary(op, subP(e))
      case e => if(e==oldV) newV else e
    }
    val (h_prime, h_pures) = subH(h)
    val res = new AState(h_prime,Pure((p map subP) ++ h_pures))

    if(res.satisfiable()) Some(res) else None
  }

  def addPure(e: Expr*) = new AState(h, Pure(p ++ e))

  def satisfiable(entry: Boolean = false): Boolean = {
    if(entry){
      ( h==Emp || h==True ) &&
        p.forall{ e =>
          Try(
            numerical.Denotational.Concrete.apply(e) match {case b:Boolean => b ; case n:Double => n != 0} // JavaScript truthiness
          ).getOrElse(false)
        }
    } else {
      h.wellformed &&
        p.forall {e => getVars(e).nonEmpty ||
          Try(
            numerical.Denotational.Concrete.apply(e) match {case b:Boolean => b ; case n:Double => n != 0} // JavaScript truthiness
          ).getOrElse(false) }
    }
  }
}
