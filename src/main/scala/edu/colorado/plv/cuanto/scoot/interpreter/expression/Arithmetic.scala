package edu.colorado.plv.cuanto.scoot
package interpreter.expression

import soot._
import soot.jimple._

import domains._
import domains.IntDom

/** Sub-interpreter for arithmetic nodes */
object Arithmetic {

  private def omerge[A,B](t: (Option[A],Option[B])): Option[(A,B)] =
    for (a <- t._1; b <- t._2) yield (a,b)


  /** Interpret an arithmetic operator node */
  def interpNode(v: Value)(r: Value => Option[RFun]):
      Option[RFun] = v match {
    // case v: Local => env get v
    case v: IntConstant => Some(_ => Some(Arith(IntDom(v.value))))
    case v: BinopExpr => for {
      op <- bop(v)
      arg1 <- r(v.getOp1())
      arg2 <- r(v.getOp2())
    } yield { e: Env => op(arg1(e), arg2(e)) }
    case v: UnopExpr => for {
      op <- uop(v)
      arg <- r(v.getOp())
    } yield { e: Env => op(arg(e)) }
    case _ => None
  }

  /** Interpret an arithmetic unary operator node, getting back a
    * function that performs the operation */
  private def uop(op: UnopExpr):
      Option[Option[R] => Option[R]] = {
    def tryOp(f: IntDom => IntDom)(a: Option[R]): Option[R] = a match {
      case Some(Arith(a)) => Some(Arith(f(a)))
      case _ => None
    }
    op match {
      case _: NegExpr => Some(tryOp((i: IntDom) => i.neg(i)))
      case _ => None
    }
  }

  /** Interpret an arithemetic binary operator node, getting back a
    * function that performs the operation */
  private def bop(op: BinopExpr):
      Option[(Option[R], Option[R]) => Option[R]] = {
    def tryOp(f: (IntDom,IntDom) => IntDom)(a: Option[R], b: Option[R]):
        Option[R] =
      (a,b) match {
        case (Some(Arith(a)),(Some(Arith(b)))) => Some(Arith(f(a,b)))
        case _ => None
      }
    op match {
      case _: AddExpr => Some(tryOp((i1: IntDom,i2: IntDom) => i1.add(i1)(i2)))
      case _: SubExpr => Some(tryOp((i1: IntDom,i2: IntDom) => i1.sub(i1)(i2)))
      case _: DivExpr => Some(tryOp((i1: IntDom,i2: IntDom) => i1.div(i1)(i2)))
      case _: MulExpr => Some(tryOp((i1: IntDom,i2: IntDom) => i1.mul(i1)(i2)))
      case _ => None
    }
  }

}
