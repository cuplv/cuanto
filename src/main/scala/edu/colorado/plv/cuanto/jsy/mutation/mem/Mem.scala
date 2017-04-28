package edu.colorado.plv.cuanto.jsy
package mutation.mem

import cats.data.State

/** Address.
  *
  * @group Intermediate AST Nodes
  */
case class A private (a: Int) extends Val

/** Memory.
  *
  * Memory is a mapping from addresses to values. It enforces the invariant that
  * addresses are allocated in this module.
  *
  * @tparam V The value type stored in this memory
  * @author Bor-Yuh Evan Chang
  */
class Mem[V] private (map: Map[A, V], nextAddr: Int) {
  def apply(key: A): V = map(key)
  def get(key: A): Option[V] = map.get(key)
  def +(kv: (A, V)): Mem[V] = new Mem[V](map + kv, nextAddr)
  def contains(key: A): Boolean = map.contains(key)
  override def toString: String = map.toString

  private def alloc(v: V): (Mem[V], A) = {
    val fresha = A(nextAddr)
    (new Mem[V](map + (fresha -> v), nextAddr + 1), fresha)
  }
}

object Mem {
  def empty[V]: Mem[V] = new Mem[V](Map.empty, 1)

  /** Get a fresh address. */
  def alloc[V](v: V): State[Mem[V], A] = State.get flatMap { m =>
    val (mp, a) = m.alloc(v)
    State.set(mp) map { _ => a }
  }
}
