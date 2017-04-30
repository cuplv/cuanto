package edu.colorado.plv.cuanto.jsy
package boolean

import edu.colorado.plv.cuanto.recursing.FixFun
import edu.colorado.plv.cuanto.abstracting.{Abstraction, Abstractable}

/** Signature for a denotational interpreter. */
trait Denotational {
  /** The type of values. */
  type V

  /** The denotation function. */
  val fun: FixFun[Expr,V]

  def apply: Expr => V = fun
}

object Denotational {
  /** Functor from a [[Evalable]] module to a [[Denotational]] module.
    *
    * @tparam W is the value type
    * @param eval defines arithmetic operations over `W`
    */
  def Make[W](eval: Evalable[W]): Denotational { type V = W } = new Denotational {
    type V = W
    import eval.{toBooleanOps, represent}

    val fun: FixFun[Expr, V] = FixFun(rec => {
      case B(b) => b
      case Unary(Not, e1) => ! rec(e1)
      case Binary(And, e1, e2) => rec(e1) && rec(e2)
      case Binary(Or, e1, e2) => rec(e1) || rec(e2)
      case If(cond, t, f) => eval.truthiness(rec(cond)) match {
        case c if c.size == 0 =>
          eval match {
            case a:Abstraction {type A = W} => a.bottom
            case _ => ??? // Undefined behavior when values aren't abstract
          }
        case c if c.size == 2 =>
          eval match {
            case a:Abstraction {type A = W} => a.join(rec(t), rec(f))
            case _ => ??? // Undefined behavior when values aren't abstract
          }
        case c  /* fall-through implies c.size==1 */ => if(c.head) rec(t) else rec(f)
      }
    })
  }

  /** Instantiate a concrete interpreter module. */
  val Concrete = Denotational.Make(new Evalable[Boolean] {
    type V = Boolean

    override def not(b:Boolean):Boolean = ! b
    override def and(l:Boolean, r:Boolean):Boolean = l && r
    override def or (l:Boolean, r:Boolean):Boolean = l || r
    override def ite(cond:Boolean, l:Boolean, r:Boolean):Boolean = if(cond) l else r
    override def truthiness(b:Boolean):Set[Boolean] = Set(b)
    override def represent(v: Any) = v match {
      case b:Boolean => b
      case _ => throw new IllegalArgumentException("Only booleans are representable in the boolean sub-language")
    }
  })

  /** Instantiate an abstract interpreter module. */
  def Abstract[D](domain : Abstraction with Evalable[D] with Abstractable[D]) = Denotational.Make[D](domain)
}