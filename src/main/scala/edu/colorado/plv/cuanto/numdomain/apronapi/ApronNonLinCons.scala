package edu.colorado.plv.cuanto.numdomain.apronapi

import apron._
import edu.colorado.plv.cuanto.numdomain._
import edu.colorado.plv.cuanto.numdomain.AbsCons

/**
  * Created by lumber on 4/27/17.
  */
class ApronNonLinCons(override val expr: ApronNonLinExpr, override val op: Cop) extends AbsCons(expr, op) {
  def cons: Tcons0 = Cons

  private val Cons: Tcons0 = op match {
    case LE => new Tcons0(Tcons0.SUPEQ, neg(expr))
    case LT => new Tcons0(Tcons0.SUP, neg(expr))
    case GE => new Tcons0(Tcons0.SUPEQ, expr.expr)
    case GT => new Tcons0(Tcons0.SUP, expr.expr)
    case NE => new Tcons0(Tcons0.DISEQ, expr.expr)
    case EQ => new Tcons0(Tcons0.EQ, expr.expr)
  }

  override def toString: String = expr.toString + " " + op + " 0"

  private def neg(expr: ApronNonLinExpr): Texpr0Node = new Texpr0BinNode(Texpr0BinNode.OP_SUB, new Texpr0CstNode(new MpqScalar(0)), expr.expr.toTexpr0Node)
}
