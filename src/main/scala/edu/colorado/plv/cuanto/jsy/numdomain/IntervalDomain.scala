package edu.colorado.plv.cuanto.jsy.numdomain

import apron._
import edu.colorado.plv.cuanto.jsy._
import edu.colorado.plv.cuanto.jsy.arithmetic._
import gmp.Mpfr

/**
  * @author Tianhan Lu
  */
object IntervalDomain {
  implicit def convert(d: Double): MpfrScalar = new MpfrScalar(d, Mpfr.RNDU)
  val ERROR_VAL = new Texpr1CstNode(-1.0)

  /**
    *
    * @param num a number
    * @return an Apron expression that wraps the number
    */
  def wrap(num: N): Texpr1Node = new Texpr1CstNode(num.n)

  /**
    *
    * @param uop unary operator
    * @param op operand could either be a double or a variable name
    * @return an Apron expression that wraps the unary expression
    */
  def wrap(uop: Uop, op: Either[Double, String]): Texpr1Node = {
    val operand = op match {
      case Left(op) => new Texpr1CstNode(op)
      case Right(op) => new Texpr1VarNode(op)
    }
    uop match {
      case Neg => genNegNode(operand)
    }
  }

  /**
    *
    * @param uop unary operator
    * @param node an Apron expression
    * @return an Apron expression that wraps the unary expression
    */
  def wrap(uop: Uop, node: Option[Texpr1Node]): Texpr1Node = {
    node match {
      case Some(node) => genNegNode(node)
      case None => ERROR_VAL
    }
  }

  /**
    *
    * @param bop binary operator
    * @param left an Apron expression
    * @param right an Apron expression
    * @return an Apron expression that wraps the binary expression
    */
  def wrap(bop: Bop, left: Option[Texpr1Node], right: Option[Texpr1Node]): Texpr1Node = {
    (left, right) match {
      case (Some(left), Some(right)) => genBinNode(bop, left, right)
      case _ => ERROR_VAL
    }
  }

  /**
    *
    * @param bop binary operator
    * @param op1 operand could either be a double or a variable name
    * @param op2 operand could either be a double or a variable name
    * @return an Apron expression that wraps the binary expression
    */
  def wrap(bop: Bop, op1: Either[Double, String], op2: Either[Double, String]): Texpr1Node = {
    val left = op1 match {
      case Left(op1) => new Texpr1CstNode(op1)
      case Right(op1) => new Texpr1VarNode(op1)
    }
    val right = op2 match {
      case Left(op2) => new Texpr1CstNode(op2)
      case Right(op2) => new Texpr1VarNode(op2)
    }
    genBinNode(bop, left, right)
  }

  /**
    *
    * @param node an Apron expression
    * @return an Apron expression that represents `0 - node`
    */
  def genNegNode(node: Texpr1Node): Texpr1Node = new Texpr1BinNode(Texpr0BinNode.OP_SUB, new Texpr1CstNode(0.0), node)

  /**
    *
    * @param bop a binary operator
    * @param left an Apron expression
    * @param right an Apron expression
    * @return an Apron expression that represents `left bop right`
    */
  def genBinNode(bop: Bop, left: Texpr1Node, right: Texpr1Node): Texpr1Node = {
    bop match {
      case Plus  => new Texpr1BinNode(Texpr1BinNode.OP_ADD, left, right)
      case Minus => new Texpr1BinNode(Texpr1BinNode.OP_SUB, left, right)
      case Times => new Texpr1BinNode(Texpr1BinNode.OP_MUL, left, right)
      case Div   => new Texpr1BinNode(Texpr1BinNode.OP_DIV, left, right)
      case x@_ => throw new Exception("Unsupported operator " + x)
    }
  }

  /**
    *
    * @param e a jsy expression
    * @return an Apron expression that represents the jsy expression
    */
  def interpret(e: Expr): Option[Texpr1Node] = {
    implicit def some: Texpr1Node => Option[Texpr1Node] = { Some(_) }

    e match {
      /* Do rules. */
      case n@N(num) => wrap(n)
      case Unary(op, N(n1)) => wrap(op, Left(n1))
      case Unary(op, edu.colorado.plv.cuanto.jsy.Var(name)) => wrap(op, Right(name))
      case Binary(op, N(n1), N(n2)) => wrap(op, Left(n1), Left(n2))
      case Binary(op, edu.colorado.plv.cuanto.jsy.Var(n1), edu.colorado.plv.cuanto.jsy.Var(n2)) => wrap(op, Right(n1), Right(n2))
      case Binary(op, edu.colorado.plv.cuanto.jsy.Var(n1), N(n2)) => wrap(op, Right(n1), Left(n2))
      case Binary(op, N(n1), edu.colorado.plv.cuanto.jsy.Var(n2)) => wrap(op, Left(n1), Right(n2))

      /* Search rules. */
      case Unary(op, e1) =>
        for {
          e1p <- interpret(e1)
        } yield
          wrap(op, e1p)
      case Binary(op, v1 @ N(_), e2) =>
        for {
          e2p <- interpret(e2)
        } yield
          wrap(op, wrap(v1), e2p)
      case Binary(op, e1, e2) =>
        for {
          e1p <- interpret(e1)
        } yield
          wrap(op, e1p, interpret(e2))

      /* Otherwise (including values) get stuck. */
      case _ => None
    }
  }
}
