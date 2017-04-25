package edu.colorado.plv.cuanto.jsy.common

import edu.colorado.plv.cuanto.jsy._
import edu.colorado.plv.cuanto.parsing.RichParsers

import scala.util.parsing.combinator.JavaTokenParsers

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
  def opAtom: Parser[Expr]

  /** Parameter: define unary operators. */
  def uop: Parser[Uop]

  /** Parameter: define unary sub-expressions. Default: [[atom]]. */
  def unarysub: Parser[Expr] = atom

  /** Parameter: define the sub-expression of blocks, such as a sequence of statements.
    * The default is [[expr]].
    */
  def statements: Parser[Expr] = expr

  /** Parameter: define types. */
  def opTyp: Parser[Typ]

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
    unarysub

  /** Parse parenthesized expressions and delegates to `opatom`.
    *
    * @see opatom
    */
  def atom: Parser[Expr] =
    opAtom |
    variable |
    parenthesized |
    block |
    failure("expected an atom")

  /** Parse a variable identifier. */
  def variable: Parser[Var] = positioned(ident ^^ Var)

  def parenthesized: Parser[Expr] =
    "(" ~> expr <~ ")"

  def block: Parser[Expr] =
    "{" ~> statements <~ "}"

  /** Parse types and delegates to `optyp`.
    *
    * @see optyp
    */
  def typ: Parser[Typ] =
    positioned("any" ^^^ TAny) |
    opTyp

}
