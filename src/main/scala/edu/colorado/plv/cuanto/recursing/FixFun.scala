package edu.colorado.plv.cuanto.recursing

import scala.util.{Failure, Success, Try}

/** A "fixfun" of type FixFun[E,R] is an abstraction of a possibly
  * recursive function of type E => R over a value of type E (expression)
  * returning an R (result) where E is an algebraic data type that
  * might be extended.
  *
  * @tparam E the input type
  * @tparam R the output type
  * @author Bor-Yuh Evan Chang
  */
class FixFun[E,R] private(private val gen: (E => R) => PartialFunction[E,R]) extends (E => R) {
  /** The recursive function. */
  private lazy val fun: E => R = FixFun.fix(gen)

  /** Apply the recursive function. */
  def apply(e: E): R = fun(e)

  /** Apply the recursive function potentially failing with a
    * [[scala.MatchError]]. Does not catch any other exceptions.
    */
  def tryApply(e: E): Try[R] =
    try Success(apply(e))
    catch { case e: MatchError => Failure(e) }

  /** Explicitly lift the partial function via `tryApply`. */
  def lift: E => Option[R] = { e => tryApply(e).toOption }

  /** Delegate to another visitor if not defined on this visitor.
    *
    * @see extendWith for arguments in opposite order
    */
  def orElse(that: FixFun[E,R]): FixFun[E,R] = FixFun { self: (E => R) =>
    gen(self) orElse that.gen(self)
  }

  /** Extend this visitor with another visitor. Try the argument `that` before `this`.
    *
    * @see orElse for arguments in opposite order
    */
  def extendWith(that: FixFun[E,R]): FixFun[E,R] = that orElse this

  /** Compose with a pre-processing function. */
  def compose(pre: E => E): FixFun[E,R] = FixFun { self: (E => R) =>
    val pf = gen(self)
    FixFun.withApply(pf, pf compose pre)
  }

  /** Compose with a post-processing function. */
  def andThen(post: R => R): FixFun[E,R] = FixFun { self: (E => R) =>
    val pf = gen(self)
    FixFun.withApply(pf, post compose pf)
  }

  /** Map the abstracted `E => R` function to another. */
  def map(op: (E => R) => (E => R)): FixFun[E,R] = FixFun { self: (E => R) =>
    val pf = gen(self)
    FixFun.withApply(pf, op(pf))
  }
}

object FixFun {
  /** Construct a fixfun.
    *
    * @param gen a generator that yields a [[scala.PartialFunction]] when
    *            given a reference to the "completed" function, that is,
    *            the parameter of type E => R is a kind of self reference
    *            that includes itself and any extensions.
    */
  def apply[E,R](gen: (E => R) => PartialFunction[E,R]): FixFun[E,R] = new FixFun(gen)

  /** Construct the recursive function.
    *
    * In other words, given the generator functional `gen`, return
    * the least fixed point of `gen`.
    */
  private def fix[E,R](gen: (E => R) => PartialFunction[E,R]): E => R = { (e: E) =>
    def fun(e: E): R = gen(fun)(e)
    fun(e)
  }

  /** Override apply on a given [[scala.PartialFunction]]. */
  private def withApply[E,R](pf: PartialFunction[E,R], app: E => R): PartialFunction[E,R] = new PartialFunction[E,R] {
    override def isDefinedAt(e: E) = pf.isDefinedAt(e)
    override def apply(e: E) = app(e)
  }
}
