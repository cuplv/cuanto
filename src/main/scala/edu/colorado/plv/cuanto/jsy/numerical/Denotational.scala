package edu.colorado.plv.cuanto.jsy
package numerical

import edu.colorado.plv.cuanto.recursing.FixFun
import edu.colorado.plv.cuanto.abstracting.{Abstraction, Abstractable}

import edu.colorado.plv.cuanto.jsy.{boolean,arithmetic}

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
    import eval.toNumericalOps

    val arithmeticDenotation : FixFun[Expr,V] = arithmetic.Denotational.Make(eval).fun
    val booleanDenotation    : FixFun[Expr,V] =    boolean.Denotational.Make(eval).fun

    val fun: FixFun[Expr, V] = arithmeticDenotation orElse booleanDenotation orElse FixFun(rec => {
      case Binary(Eq, e1, e2) => rec(e1) === rec(e2)
      case Binary(Ne, e1, e2) => rec(e1) !== rec(e2)
      case Binary(Le, e1, e2) => rec(e1) <=  rec(e2)
      case Binary(Lt, e1, e2) => rec(e1) <   rec(e2)
      case Binary(Ge, e1, e2) => rec(e1) >   rec(e2)
      case Binary(Gt, e1, e2) => rec(e1) >=  rec(e2)
    })
  }

  /** Instantiate a concrete interpreter module. */
  val Concrete = Denotational.Make(new Evalable[Either[Double,Boolean]] {
    type V = Either[Double,Boolean]

    override def not(v: V): V = v match { case Right(b) => Right(!b) ; case _ => throw new UnsupportedOperationException }
    override def and(l: V, r: V): V = (l,r) match { case (Right(l), Right(r)) => Right(l && r) ; case _ => throw new UnsupportedOperationException }
    override def or(l: V, r: V): V = (l,r) match { case (Right(l), Right(r)) => Right(l || r) ; case _ => throw new UnsupportedOperationException }
    override def ite(cond: V, l: V, r: V): V = cond match { case Right(cond) => if(cond) l else r ; case _ => throw new UnsupportedOperationException }

    override def divide(l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Left( l / r ) ; case _ => throw new UnsupportedOperationException }
    override def minus (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Left( l - r ) ; case _ => throw new UnsupportedOperationException }
    override def plus  (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Left( l + r ) ; case _ => throw new UnsupportedOperationException }
    override def times (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Left( l * r ) ; case _ => throw new UnsupportedOperationException }
    override def negate(v : V): V = v match { case Left(v) => Left( - v) ; case _ => throw new UnsupportedOperationException }

    override def equ(l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l == r ) ; case _ => throw new UnsupportedOperationException }
    override def neq(l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l != r ) ; case _ => throw new UnsupportedOperationException }
    override def ge (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l >= r ) ; case _ => throw new UnsupportedOperationException }
    override def gt (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l >  r ) ; case _ => throw new UnsupportedOperationException }
    override def le (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l <= r ) ; case _ => throw new UnsupportedOperationException }
    override def lt (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l <  r ) ; case _ => throw new UnsupportedOperationException }


    override def truthiness(v: V): Set[Boolean] = Set( v fold ( {d => d != 0}, {b => b} ) )

    override def represent(v:Any): V = v match {
      case d:Double  => Left(d)
      case b:Boolean => Right(b)
      case _ => throw new UnsupportedOperationException("Only doubles and booleans are representable in the numerical sublanguage")
    }
  })

  /** Instantiate an abstract interpreter module. */
  def Abstract[D](domain : Abstraction with Evalable[D] with Abstractable[D]) = Denotational.Make[D](domain)

}