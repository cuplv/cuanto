package edu.colorado.plv.cuanto.jsy.numdomain

import apron._
import edu.colorado.plv.cuanto.jsy._
import edu.colorado.plv.cuanto.jsy.arithmetic._
import gmp.Mpfr

import scala.collection.immutable.HashMap

/**
  * @author Tianhan Lu
  */
object IntervalDomain {
  implicit def convert(d: Double): MpfrScalar = new MpfrScalar(d, Mpfr.RNDU)

  def denote(uop: Uop, op: Either[Double, String]): Texpr1BinNode = {
    val operand = op match {
      case Left(op) => new Texpr1CstNode(op)
      case Right(op) => new Texpr1VarNode(op)
    }
    uop match {
      case Neg => new Texpr1BinNode(Texpr0BinNode.OP_SUB, new Texpr1CstNode(0.0), operand)
    }
  }

  def denote(bop: Bop, op1: Either[Double, String], op2: Either[Double, String]): Texpr1BinNode = {
    val left = op1 match {
      case Left(op1) => new Texpr1CstNode(op1)
      case Right(op1) => new Texpr1VarNode(op1)
    }
    val right = op2 match {
      case Left(op2) => new Texpr1CstNode(op2)
      case Right(op2) => new Texpr1VarNode(op2)
    }
    bop match {
      case Plus  => new Texpr1BinNode(Texpr1BinNode.OP_ADD, left, right)
      case Minus => new Texpr1BinNode(Texpr1BinNode.OP_SUB, left, right)
      case Times => new Texpr1BinNode(Texpr1BinNode.OP_MUL, left, right)
      case Div   => new Texpr1BinNode(Texpr1BinNode.OP_DIV, left, right)
      case x@_ => throw new Exception("Unsupported operator " + x)
    }
  }

  def interpret(e: Expr): Option[Texpr1Node] = {
    implicit def some: Texpr1Node => Option[Texpr1Node] = { Some(_) }

    e match {
      /* Do rules. */
      case Unary(op, N(n1)) => denote(op, Left(n1))
      case Unary(op, edu.colorado.plv.cuanto.jsy.Var(name)) => denote(op, Right(name))
      case Binary(op, N(n1), N(n2)) => denote(op, Left(n1), Left(n2)
      case Binary(op, edu.colorado.plv.cuanto.jsy.Var(n1), edu.colorado.plv.cuanto.jsy.Var(n2)) => denote(op, Right(n1), Right(n2))
      case Binary(op, edu.colorado.plv.cuanto.jsy.Var(n1), N(n2)) => denote(op, Right(n1), Left(n2))
      case Binary(op, N(n1), edu.colorado.plv.cuanto.jsy.Var(n2)) => denote(op, Left(n1), Right(n2))

      /* Search rules. */
      case Unary(op, e1) =>
        for {
          e1p <- interpret(e1)
        } yield
          Unary(op, e1p)
      case Binary(op, v1 @ N(_), e2) =>
        for {
          e2p <- interpret(e2)
        } yield
          Binary(op, v1, e2p)
      case Binary(op, e1, e2) =>
        for {
          e1p <- interpret(e1)
        } yield
          Binary(op, e1p, e2)

      /* Otherwise (including values) get stuck. */
      case _ => None
    }
  }
}
