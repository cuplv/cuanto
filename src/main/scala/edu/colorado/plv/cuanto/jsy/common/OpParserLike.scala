package edu.colorado.plv.cuanto.jsy.common

import edu.colorado.plv.cuanto.jsy._
import edu.colorado.plv.cuanto.parsing.RichParsers

import scala.util.parsing.combinator.JavaTokenParsers
import scala.util.parsing.input.Positional

/** Parser components that handle unary and binary operators.
  *
  * This trait implements standard non-terminals for an expression language
  * with unary and binary operators.
  *
  * ==Abstract Syntax==
  *
  * This trait implements a parser for the following abstract syntax
  * consisting of atoms, unary, and binary expressions.
  *
  *   - ''expr'' ::= ''atom'' | ''uop'' ''expr'' | ''expr'' ''bop'' ''expr''
  *
  * ==Concrete Syntax==
  *
  * Specifically, it implements a parser given a specification for the atoms,
  * unary, and binary operators for the following concrete syntax:
  *
  *   - ''binary,,1,,'' ::= ''binary,,2,,'' { ''bop,,1,,'' ''binary,,2,,'' }
  *   - ...
  *   - ''binary,,n,,'' ::= ''unary'' { ''bop,,n,,'' ''unary'' }
  *   - ''unary'' ::= ''uop'' ''unary'' | ''atom''
  *   - ''atom'' ::= ''opatom'' | `(` ''expr'' `)` | `{` ''expr'' `}`
  *
  * The { α } is in the meta-language to indicate 0-or-more α's (i.e., EBNF).
  *
  * The ''expr'', ''opatom'', and ''uop'' symbols are specified by defining
  * [[scala.util.parsing.combinator.Parsers#Parser]]s.
  *
  * The ''bop'' symbols is specified by defining `bop`, which should be a
  * list of list of concrete-abstract syntax pairs. The outer list specifies
  * the precedence levels from lowest to highest, while the inner list are
  * the operators at the same level. All operators are treated as
  * left-associative.
  *
  * @example {{{
  *
  *   trait ArithmeticParser extends OpParserLike with JavaTokenParsers {
  *     override def opatom: Parser[Expr] =
  *       positioned {
  *         floatingPointNumber ^^ (s => N(s.toDouble))
  *       }
  *     override def uop: Parser[Uop] =
  *       "-" ^^ { _ => Neg }
  *     override def bop: OpPrecedence = Seq(
  *       /* lowest */
  *       Seq("+" -> Plus, "-" -> Minus),
  *       Seq("*" -> Times, "/" -> Div)
  *       /* highest */
  *     )
  *   }
  *
  * }}}
  *
  * @author Bor-Yuh Evan Chang
  */
trait OpParserLike extends JavaTokenParsers with RichParsers {

  /** Parameter: define expressions. */
  def expr: Parser[Expr]

  /** Parameter: define atoms. */
  def opatom: Parser[Expr]

  /** Parameter: define unary operators. */
  def uop: Parser[Uop]

  /** Parameter: define statements (the sub-expression of blocks).
    * The default is [[expr]].
    */
  def stmt: Parser[Expr] = expr

  /** Parameter: define types. */
  def optyp: Parser[Typ]

  /** Type alias for the list defining the precedence of binary operators.
    *
    * @see [[bop]] for defining the precedence of binary operators.
    */
  type OpPrecedence = Seq[Seq[(String,Bop)]]

  /** Parameter: define precedence of left-associative binary operators.
    *
    * Specified from lowest to highest, consisting of pairs of
    * concrete-abstract syntax.
    */
  val bop: OpPrecedence

  /** A generic parser component that yields a parser for
    * left-associative binary operators.
    *
    * @param ops the binary operators, specifying precedence
    * @param sub the parser for sub-expressions
    */
  def binaryLeft(ops: OpPrecedence, sub: => Parser[Expr]): Parser[Expr] = {
    def binaryCase(opsyn: (String, Bop)): Parser[(Expr, Expr) => Expr] = {
      val (csyn, asyn) = opsyn
      withpos(csyn) ^^ { case (pos, _) => (e1: Expr, e2: Expr) => Binary(asyn, e1, e2) setPos pos }
    }
    def level(ops: Seq[(String, Bop)]): Parser[(Expr, Expr) => Expr] = {
      val op1 :: t = ops
      t.foldLeft(binaryCase(op1)) { (acc, op) => acc | binaryCase(op) }
    }
    ops.foldRight(sub) { (lops, acc) => acc * level(lops) }
  }

  /** Yields a parser for binary expressions with an instantiation for `bop`.
    *
    * @see bop
    */
  def binary: Parser[Expr] = binaryLeft(bop, unary)

  /** Yields a parser for unary expressions with an instantiation for `uop`
    *
    * @see uop
    */
  def unary: Parser[Expr] =
    positioned {
      uop ~ unary ^^ { case op ~ e => Unary(op, e) }
    } |
    atom

  /** Parse parenthesized expressions and delegates to `opatom`.
    *
    * @see opatom
    */
  def atom: Parser[Expr] =
    opatom |
    positioned(ident ^^ Var) |
    parenthesized |
    block |
    failure("expected an atom")

  def parenthesized: Parser[Expr] =
    positioned {
      "(" ~> expr <~ ")"
    }

  def block: Parser[Expr] =
    positioned {
      "{" ~> stmt <~ "}"
    }

  /** Parse types and delegates to `optyp`.
    *
    * @see optyp
    */
  def typ: Parser[Typ] =
    "any" ^^^ TAny |
    optyp

}

object OpParserLike {

  /** Statements.
    *
    * Statements only exist in the concrete syntax, so they are eliminated during parsing.
    */
  sealed abstract class Stmt extends Positional

  /** An expression as a statement. */
  case class E(e: Expr) extends Stmt

  /** Declarations.
    *
    * A declaration is a parser that takes a continuation Expr to yield an Expr.
    */
  case class Decl(d: Expr => Expr) extends Stmt

  /** Skip: unit statement */
  case object Skip extends Stmt

}