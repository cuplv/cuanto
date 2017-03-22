package edu.colorado.plv.cuanto.jsy
package arithmetic

/** Implement interpreters in the standard interpreter styles as examples.
  *
  * @author Bor-Yuh Evan Chang
  */
@deprecated("examples", "0.0.0")
object SimpleInterpreter {

  def denote(uop: Uop): Double => Double = uop match {
    case Neg => { - _ }
  }

  def denote(bop: Bop): (Double, Double) => Double = bop match {
    case Plus => { _ + _ }
    case Minus => { _ - _ }
    case Times => { _ * _ }
    case Div => { _ / _ }
  }

  /** Denotational.
    *
    * The denotation of an expression ''e'' is a number ''n'', that is,
    * `[``[` ''e'' `]``]` = ''n''.
    */
  def denote(e: Expr): Double = e match {
    case N(n) => n
    case Unary(op, e1) => denote(op)(denote(e1))
    case Binary(op, e1, e2) =>
      val (n1, n2) = (denote(e1), denote(e2))
      denote(op)(n1, n2)
  }

  /** Structural big-step or natural-style.
    *
    * We make the distinction that the denotation of an expression is
    * a number, but a big-step interpreter is a relation between
    * an expression and a value (in the object language).
    */
  def bigstep(e: Expr): Expr = e match {
    case N(_) => e
    case Unary(op, e1) => bigstep(e1) match {
      case N(n1) =>
        N(denote(op)(n1))
    }
    case Binary(op, e1, e2) => ((bigstep(e1), bigstep(e2)): @unchecked) match {
      case (N(n1), N(n2)) =>
        N(denote(op)(n1, n2))
    }
  }

  /** Structural small-step.
    *
    * @return None when stuck.
    */
  def smallstep(e: Expr): Option[Expr] = {
    implicit def some: Expr => Option[Expr] = { Some(_) }
    e match {
        /* Do rules. */
      case Unary(op, N(n1)) => N(denote(op)(n1))
      case Binary(op, N(n1), N(n2)) => N(denote(op)(n1,n2))

        /* Search rules. */
      case Unary(op, e1) =>
        for {
          e1p <- smallstep(e1)
        } yield
          Unary(op, e1p)
      case Binary(op, v1 @ N(_), e2) =>
        for {
          e2p <- smallstep(e2)
        } yield
          Binary(op, v1, e2p)
      case Binary(op, e1, e2) =>
        for {
          e1p <- smallstep(e1)
        } yield
          Binary(op, e1p, e2)

      /* Otherwise (including values) get stuck. */
      case _ => None
    }
  }

  /** Iterate a function `f` until `None`. */
  def iterate[T](t: T)(f: T => Option[T]): T = f(t) match {
    case None => t
    case Some(t) => iterate(t)(f)
  }

  /** Represent a continuation. */
  type Cont = Expr => Expr

  /** Abstract machine. */
  def machine(ek: (Expr,Cont)): (Expr, Cont) = {
    val (e, k) = ek
    e match {
        /* Continue. */
      case N(_) => (k(e), identity)

      case Unary(op, N(n1)) => (N(denote(op)(n1)), k)
      case Binary(op, N(n1), N(n2)) => (N(denote(op)(n1,n2)), k)

      case Unary(op, e1) => (e1, { (v1: Expr) => k(Unary(op, v1)) })
      case Binary(op, v1 @ N(_), e2) => (e2, { (v2: Expr) => k(Binary(op, v1, v2)) })
      case Binary(op, e1, e2) => (e1, { (v1: Expr) => k(Binary(op, v1, e2)) })
    }
  }

  /** Compute a (post) fixed point of `f` using ordering `le`.  */
  def postfixedpoint[T](t: T)(f: T => T)(implicit le: (T,T) => Boolean): T = {
    val tp = f(t)
    if (le(tp,t)) t else postfixedpoint(tp)(f)(le)
  }

}
