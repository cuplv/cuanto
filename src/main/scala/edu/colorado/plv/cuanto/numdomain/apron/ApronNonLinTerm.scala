package edu.colorado.plv.cuanto.numdomain.apron

import apron._
import edu.colorado.plv.cuanto.numdomain._

/**
  * Created by lumber on 4/27/17.
  */
class ApronNonLinTerm extends AbsTerm {
  private var Term: Texpr0Node = _

  def term: Texpr0Node = Term

  private def init(op: Bop, left: Texpr0Node, right: Texpr0Node): Texpr0Node = {
    op match {
      case ADD => new Texpr0BinNode(Texpr0BinNode.OP_ADD, left, right)
      case SUB => new Texpr0BinNode(Texpr0BinNode.OP_SUB, left, right)
      case MUL => new Texpr0BinNode(Texpr0BinNode.OP_MUL, left, right)
      case DIV => new Texpr0BinNode(Texpr0BinNode.OP_DIV, left, right)
      case MOD => new Texpr0BinNode(Texpr0BinNode.OP_MOD, left, right)
      case POW => new Texpr0BinNode(Texpr0BinNode.OP_MUL, left, right)
    }
  }

  override def toString: String = Term.toString

  def this(op: Bop, left: ApronNonLinTerm, right: ApronNonLinTerm) {
    this
    Term = init(op, left.term, right.term)
  }

  def this(op: Bop, dim1: Int, const: Double) {
    this
    Term = init(op, new Texpr0DimNode(dim1), new Texpr0CstNode(new MpfrScalar(const, ROUNDING)))
  }

  def this(op: Bop, const: Double, dim2: Int) {
    this
    Term = init(op, new Texpr0CstNode(new MpfrScalar(const, ROUNDING)), new Texpr0DimNode(dim2))
  }

  def this(op: Bop, dim1: Int, dim2: Int) = {
    this
    Term = init(op, new Texpr0DimNode(dim1), new Texpr0DimNode(dim2))
  }

  def this(coeff: Double) {
    this
    Term = new Texpr0CstNode(new MpfrScalar(coeff, ROUNDING))
  }

  def this(coeff: Int) {
    this
    Term = new Texpr0CstNode(new MpqScalar(coeff))
  }
}
