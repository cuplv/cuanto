package edu.colorado.plv.cuanto.scoot.interpreter
package expression

import soot._
import soot.jimple._

////////////////////////////////////////////////////////////////////////

object Arithmetic {

  def omerge[A,B](t: (Option[A],Option[B])): Option[(A,B)] =
    for (a <- t._1; b <- t._2) yield (a,b)


  def interpNode(v: Value)(r: Value => Option[RFun]):
      Option[RFun] = v match {
    // case v: Local => env get v
    case v: IntConstant => Some(_ => Some(IntR(v.value)))
    case v: BinopExpr => for {
      op <- bop(v)
      arg1 <- r(v.getOp1())
      arg2 <- r(v.getOp2())
    } yield { e: Env => op(arg1(e), arg2(e)) }
    case v: UnopExpr => for {
      op <- uop(v)
      arg <- r(v.getOp())
    } yield { e: Env => op(arg(e)) }
  }

  /** Interpret an arithmetic unary operator node, getting back a
    * function that performs the operation */
  private def uop(op: UnopExpr):
      Option[Option[Result] => Option[Result]] = {
    def tryOp(f: Int => Int)(a: Option[Result]): Option[Result] = a match {
      case Some(IntR(a)) => Some(IntR(f(a)))
      case _ => None
    }
    op match {
      case _: NegExpr => Some(tryOp(_ * -1))
      case _ => None
    }
  }

  /** Interpret an arithemetic binary operator node, getting back a
    * function that performs the operation */
  private def bop(op: BinopExpr):
      Option[(Option[Result], Option[Result]) => Option[Result]] = {
    def tryOp(f: (Int,Int) => Int)(a: Option[Result], b: Option[Result]):
        Option[Result] =
      (a,b) match {
        case (Some(IntR(a)),(Some(IntR(b)))) => Some(IntR(f(a,b)))
        case _ => None
      }
    op match {
      case _: AddExpr => Some(tryOp(_ + _))
      case _: SubExpr => Some(tryOp(_ - _))
      case _: DivExpr => Some(tryOp(_ / _))
      case _: MulExpr => Some(tryOp(_ * _))
      case _ => None
    }
  }

}
