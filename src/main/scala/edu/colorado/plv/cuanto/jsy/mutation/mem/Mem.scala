package edu.colorado.plv.cuanto.jsy
package mutation.mem

import cats.data.State

/** Address.
  *
  * @group Intermediate AST Nodes
  */
case class A private[mem] (a: Int) extends Val

/** Memory.
  *
  * Memory is a mapping from addresses to values. It enforces the invariant that
  * addresses are allocated in this module.
  *
  * @author Bor-Yuh Evan Chang
  */
class Mem private[mem](map: Map[A, Expr], nextAddr: Int) {
  def apply(key: A): Expr = map(key)
  def get(key: A): Option[Expr] = map.get(key)
  def +(kv: (A, Expr)): Mem = new Mem(map + kv, nextAddr)
  def contains(key: A): Boolean = map.contains(key)
  override def toString: String = map.toString

  private[Mem] def alloc(v: Expr): (Mem, A) = {
    val fresha = A(nextAddr)
    (new Mem(map + (fresha -> v), nextAddr + 1), fresha)
  }
}

object Mem {
  val empty: Mem = new Mem(Map.empty, 1)

  /** Get a fresh address. */
  def alloc(v: Expr): State[Mem, A] = State.get flatMap { m =>
    val (mp, a) = m.alloc(v)
    State.set(mp) map { _ => a }
  }
}
