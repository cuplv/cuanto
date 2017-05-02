package edu.colorado.plv.cuanto.jsy
package arithmetic

/**
  * Created by Jared on 5/1/2017.
  */
trait Semantics[E,R] {
  //None represents an error/stuck state
  def step(e: E): Option[Either[E,R]]

  //fully evaluating an expression is stepping it until it errors or is a final expression
  def steps(e: E): Option[R] = {
    step(e) match {
      case None => None
      case Some(res) =>
        res match {
          case Left(e2) => steps(e2)
          case Right(fin) => Some(fin)
        }
    }
  }
}

class DenotationalSem extends Semantics[Expr, Double] {
  //stolen
  def denote(uop: Uop): Double => Double = uop match {
    case Neg => { - _ }
  }

  def denote(bop: Bop): (Double, Double) => Double = bop match {
    case Plus => { _ + _ }
    case Minus => { _ - _ }
    case Times => { _ * _ }
    case Div => { _ / _ }
  }

  def step(e: Expr): Option[Either[Expr,Double]] = e match {
    case N(n) => Some(Right(n))
    case Unary(op, e1) =>
      steps(e1) match {
        case None => None
        case Some(n) => Some(Right(denote(op)(n)))
      }
    case Binary(op, e1, e2) =>
      (steps(e1), steps(e2)) match {
        case (Some(n1), Some(n2)) => Some(Right(denote(op)(n1, n2)))
        case _ => None
      }
  }
}

//I skipped BigStep, because it looks like Denotational but with Expr as result type

class SmallStepSem extends Semantics[Expr, Expr] {
  def denote(uop: Uop): Double => Double = uop match {
    case Neg => { - _ }
  }

  def denote(bop: Bop): (Double, Double) => Double = bop match {
    case Plus => { _ + _ }
    case Minus => { _ - _ }
    case Times => { _ * _ }
    case Div => { _ / _ }
  }

  def step(e: Expr): Option[Either[Expr,Expr]] = e match {
    case Unary(op, N(n1)) => Some(Right(N(denote(op)(n1))))
    case Binary(op, N(n1), N(n2)) => Some(Right(N(denote(op)(n1, n2))))

    case Unary(op, e1) =>
      step(e1) match {
        case None => None
        case Some(Left(e2)) => Some(Left(Unary(op, e2)))
        case Some(Right(e2)) => Some(Left(Unary(op, e2))) //code reuse - how to factor out?
      }

    case Binary(op, v1 @ N(_), e2) =>
      step(e2) match {
        case None => None
        case Some(Left(e3)) => Some(Left(Binary(op, v1, e3)))
        case Some(Right(e3)) => Some(Left(Binary(op, v1, e3)))
      }

    case Binary(op, e1, e2) =>
      step(e1) match {
        case None => None
        case Some(Left(e3)) => Some(Left(Binary(op, e3, e2)))
        case Some(Right(e3)) => Some(Left(Binary(op, e3, e2)))
      }

    case _ => None
  }
}

class MachineSem extends Semantics[(Expr, Expr => Expr), (Expr, Expr => Expr)] {
  type Cont = Expr => Expr

  def denote(uop: Uop): Double => Double = uop match {
    case Neg => { - _ }
  }

  def denote(bop: Bop): (Double, Double) => Double = bop match {
    case Plus => { _ + _ }
    case Minus => { _ - _ }
    case Times => { _ * _ }
    case Div => { _ / _ }
  }

  def step(ek: (Expr,Cont)): Option[Either[(Expr,Cont),(Expr,Cont)]] = {
    val (e, k) = ek
    e match {
      case N(_) => Some(Right( (k(e), identity) ))

      case Unary(op, N(n1)) => Some(Left( (N(denote(op)(n1)), k) ))
      case Binary(op, N(n1), N(n2)) => Some(Left( (N(denote(op)(n1,n2)), k) ))

      case Unary(op, e1) => Some(Left( (e1, { (v1: Expr) => k(Unary(op, v1)) }) ))
      case Binary(op, v1 @ N(_), e2) => Some(Left( (e2, { (v2: Expr) => k(Binary(op, v1, v2)) }) ))
      case Binary(op, e1, e2) => Some(Left( (e1, { (v1: Expr) => k(Binary(op, v1, e2)) }) ))
    }
  }
}

/*
Discussion/extensions:
Named errors: can be handled by changing step's return type from Option(...) to Either(...)
  Left becomes a named Error class rather than None
Extending to places where Env is necessary: should be doable by just passing an Env through?
 */