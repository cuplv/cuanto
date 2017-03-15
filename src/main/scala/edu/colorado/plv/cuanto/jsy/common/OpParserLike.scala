package edu.colorado.plv.cuanto.jsy.common

import edu.colorado.plv.cuanto.jsy._
import edu.colorado.plv.cuanto.parsing.RichParsers

import scala.util.parsing.combinator.RegexParsers

/** Parser components that handle unary and binary operators.
  *
  * @author Bor-Yuh Evan Chang
  */
trait OpParserLike extends RegexParsers with RichParsers {
  /** Define expressions. */
  def expr: Parser[Expr]

  /** Define atoms. */
  def opatom: Parser[Expr]

  /** Define unary operators. */
  def uop: Parser[Uop]

  /** Precedence of binary operators.
    *
    * Specified from lowest to highest, consisting of pairs of
    * concrete-abstract syntax.
    */
  type OpPrecedence = List[List[(String,Bop)]]

  /** Define precedence of left-associative binary operators. */
  val bop: OpPrecedence

  /** Parser for binary expressions.
    *
    * ''binary,,1,,'' ::= ''binary,,2,,'' { ''bop,,1,,'' ''binary,,2,,'' }
    *
    * ...
    *
    * ''binary,,n,,'' ::= ''unary'' { ''bop,,n,,'' ''unary'' }
    */
  def binary: Parser[Expr] = {
    def binaryOps(ops: OpPrecedence): Parser[Expr] = {
      def binaryCase(opsyn: (String, Bop)): Parser[(Expr, Expr) => Expr] = {
        val (csyn, asyn) = opsyn
        withpos(csyn) ^^ { case (pos, _) => (e1: Expr, e2: Expr) => Binary(asyn, e1, e2) setPos pos }
      }
      def level(ops: List[(String, Bop)]): Parser[(Expr, Expr) => Expr] = {
        val op1 :: t = ops
        (binaryCase(op1) /: t) { (acc, op) => acc | binaryCase(op) }
      }
      (ops :\ unary) { (lops, acc) => acc * level(lops) }
    }
    binaryOps(bop)
  }

  /** Parser for unary expressions.
    *
    * ''unary'' ::= ''uop'' ''unary'' | ''atom''
    */
  def unary: Parser[Expr] =
    positioned {
      uop ~ unary ^^ { case op ~ e => Unary(op, e) }
    } |
    atom

  /** ''atom'' ::= ''opatom'' | '(' ''expr'' ')' */
  def atom: Parser[Expr] =
    opatom |
    "(" ~> expr <~ ")" |
    failure("expected an atom")
}
