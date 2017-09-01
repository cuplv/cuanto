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

  def denote(uop: Uop, op: Either[Double, Int]): Texpr0BinNode = {
    val operand = op match {
      case Left(op) => new Texpr0CstNode(op)
      case Right(op) => new Texpr0DimNode(op)
    }
    uop match {
      case Neg => new Texpr0BinNode(Texpr0BinNode.OP_SUB, new Texpr0CstNode(0.0), operand)
    }
  }

  def denote(bop: Bop, op1: Either[Double, Int], op2: Either[Double, Int]): Texpr0BinNode = {
    val left = op1 match {
      case Left(op1) => new Texpr0CstNode(op1)
      case Right(op1) => new Texpr0DimNode(op1)
    }
    val right = op2 match {
      case Left(op2) => new Texpr0CstNode(op2)
      case Right(op2) => new Texpr0DimNode(op2)
    }
    bop match {
      case Plus  => new Texpr0BinNode(Texpr0BinNode.OP_ADD, left, right)
      case Minus => new Texpr0BinNode(Texpr0BinNode.OP_SUB, left, right)
      case Times => new Texpr0BinNode(Texpr0BinNode.OP_MUL, left, right)
      case Div   => new Texpr0BinNode(Texpr0BinNode.OP_DIV, left, right)
      case x@_ => throw new Exception("Unsupported operator " + x)
    }
  }

  def interpret(e: Expr, symtable: HashMap[String, Int]): (Option[Texpr0Node], HashMap[String, Int]) = {
    implicit def some: Texpr0Node => Option[Texpr0Node] = { Some(_) }

    def getIdx(name: String, symtable: HashMap[String, Int]): (Int, HashMap[String, Int]) = {
      symtable.get(name) match {
        case Some(idx) => (idx, symtable)
        case None => (symtable.size + 1, symtable + (name -> (symtable.size + 1)))
      }
    }

    e match {
      /* Do rules. */
      case Unary(op, N(n1)) => (denote(op, Left(n1)), symtable)
      case Unary(op, Var(name)) =>
        val (idx, newSymtable) = getIdx(name, symtable)
        (denote(op, Right(idx)), newSymtable)
      case Binary(op, N(n1), N(n2)) => (denote(op, Left(n1), Left(n2)), symtable)
      case Binary(op, Var(n1), Var(n2)) =>
        val (idx1, newSymtable1) = getIdx(n1, symtable)
        val (idx2, newSymtable2) = getIdx(n2, newSymtable1)
        (denote(op, Right(idx1), Right(idx2)), newSymtable2)
      case Binary(op, Var(n1), N(n2)) =>
        val (idx1, newSymtable) = getIdx(n1, symtable)
        (denote(op, Right(idx1), Left(n2)), newSymtable)
      case Binary(op, N(n1), Var(n2)) =>
        val (idx2, newSymtable) = getIdx(n2, symtable)
        (denote(op, Left(n1), Right(idx2)), newSymtable)

      /* Search rules. */
      case Unary(op, e1) =>
        for {
          (e1p, newSymtable) <- interpret(e1, symtable)
        } yield
          (Unary(op, e1p), newSymtable)
      case Binary(op, v1 @ N(_), e2) =>
        for {
          (e2p, newSymtable) <- interpret(e2, symtable)
        } yield
          (Binary(op, v1, e2p), newSymtable)
      case Binary(op, e1, e2) =>
        for {
          (e1p, newSymtable) <- interpret(e1, symtable)
        } yield
          (Binary(op, e1p, e2), newSymtable)

      /* Otherwise (including values) get stuck. */
      case _ => (None, symtable)
    }
  }
}
