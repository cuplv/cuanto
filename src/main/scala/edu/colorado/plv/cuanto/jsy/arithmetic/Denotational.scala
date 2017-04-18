package edu.colorado.plv.cuanto.jsy
package arithmetic

import edu.colorado.plv.cuanto.recursing.FixFun
import edu.colorado.plv.cuanto.abstracting.{Abstraction, Abstractable}

/** Signature for a denotational interpreter. */
trait Denotational {
  /** The type of values. */
  type V

  /** The denotation function.  */
  val fun: FixFun[Expr, V]

  /** Evaluate an expression to a value. */
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
    import eval.{toArithmeticOps,represent}

    val fun: FixFun[Expr, V] = FixFun(rec => {
      case N(n) => n
      case Unary(Neg, e1) => - rec(e1)
      case Binary(Plus, e1, e2) => rec(e1) + rec(e2)
      case Binary(Minus, e1, e2) => rec(e1) - rec(e2)
      case Binary(Times, e1, e2) => rec(e1) * rec(e2)
      case Binary(Div, e1, e2) => rec(e1) / rec(e2)
    })
  }

  /** Instantiate a concrete interpreter module. */
  val Concrete = Denotational.Make(new Evalable[Double] {
    type V = Double
    override def negate(v1: Double) = - v1
    override def plus(v1: Double, v2: Double) = v1 + v2
    override def minus(v1: Double, v2: Double) = v1 - v2
    override def times(v1: Double, v2: Double) = v1 * v2
    override def divide(v1: Double, v2: Double) = v1 / v2
    override def represent(v: Any) = v match {
      case d:Double => d
      case _ => throw new IllegalArgumentException("Only doubles are representable in the arithmetic sub-language")
    }
  })

  /** Instantiate an abstract interpreter module. */
  def Abstract[D](domain : Abstraction with Evalable[D] with Abstractable[D]) = Denotational.Make[D](domain)

}
