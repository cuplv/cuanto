package edu.colorado.plv.cuanto.jsy.arithmetic

import edu.colorado.plv.cuanto.jsy.arithmetic.ast._
import edu.colorado.plv.cuanto.parsing.ParserLike

import scala.util.parsing.combinator.JavaTokenParsers
import scala.util.parsing.input.Reader

/** Parse into the arithmetic sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
object Parser extends JavaTokenParsers with ParserLike[Expr] {
  override def scan(in: Reader[Char]): Input = in
  override def start: Parser[Expr] = expr

  /* Specify the syntax. */

  /** Parser for expressions ''expr'': [[Expr]].
    *
    * ''expr'' ::=
    *
    */
  def expr: Parser[Expr] = binary(bop)

  /** Parser for binary expressions.
    *
    * ''binary,,1,,'' ::= ''binary,,2,,'' { ''bop,,1,,'' ''binary,,2,,'' }
    *
    * ...
    *
    * ''binary,,n,,'' ::= ''unary'' { ''bop,,n,,'' ''unary'' }
    *
    */
  def binary(ops: OpPrecedence): Parser[Expr] = {
    def level(ops: List[(String, Bop)]): Parser[(Expr, Expr) => Expr] = {
      val op1 :: t = ops
      (prebinary(op1) /: t) { (acc, op) => acc | prebinary(op) }
    }
    (ops :\ unary) { (lops, acc) => acc * level(lops) }
  }

  /** ''unary'' ::= ''uop'' ''unary'' | ''atom'' */
  def unary: Parser[Expr] =
    positioned {
      uop ~ unary ^^ { case op ~ e => Unary(op, e) }
    } |
    atom

  /** ''atom'' ::= ''float'' | '(' ''expr'' ')' */
  def atom: Parser[Expr] =
    positioned {
      floatingPointNumber ^^ (s => N(s.toDouble))
    } |
    "(" ~> expr <~ ")" |
    failure("expected an atom")

  /** ''uop'' ::= '-' */
  def uop: Parser[Uop] =
    "-" ^^ { _ => Neg }

  /** Define precedence of left-associative binary operators.
    *
    * ''bop'' ::= '+' | '-' | '*' | '/'
    */
  lazy val bop: OpPrecedence = List(
    /* lowest */
    List("+" -> Plus, "-" -> Minus),
    List("*" -> Times, "/" -> Div)
    /* highest */
  )

  type OpPrecedence = List[List[(String,Bop)]]

  def prebinary(opsyn: (String, Bop)): Parser[(Expr, Expr) => Expr] = {
    val (csyn, asyn) = opsyn
    withpos(csyn) ^^ { case (pos, _) => (e1: Expr, e2: Expr) => Binary(asyn, e1, e2) setPos pos }
  }
}
