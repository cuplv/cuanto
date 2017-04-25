package edu.colorado.plv.cuanto.jsy
package binding

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

import scala.util.parsing.input.Positional

/** Parse variables and bindings.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  import ParserLike._

  override def exprBlock: Parser[Expr] = {
    rep(concreteStmt) ^^ { stmts =>
      stmts.foldRight(None: Option[Expr]) {
        case (Skip, eopt) => eopt
        case (E(e), None) => Some(e)
        case (E(e1), Some(e2)) => Some(Seq(e1, e2))
        case (Decl(d), None) => Some(d(Unit))
        case (Decl(d), Some(e2)) => Some(d(e2))
      }.getOrElse(Unit)
    }
  }

  def concreteDecl: Parser[Decl] =
    (("let" | "const") ~> withpos(ident)) ~ withpos("=" ~> expr) ^^ {
      case (posx, x) ~ ((pos1, e1)) => Decl(e2 => Bind(Var(x) setPos posx, e1, e2) setPos pos1)
    }

  def concreteStmt: Parser[Stmt] =
    ";" ^^^ Skip |
    concreteDecl |
    (expr | block) ^^ E

  abstract override def opAtom: Parser[Expr] =
    positioned {
      "undefined" ^^^ Unit
    } |
    block |
    super.opAtom

  lazy val seqBop: OpPrecedence = List(List("," -> Seq))

  /** Parameter: define the non-terminal for the sub-expressions of sequencing expressions. */
  def seqsub: Parser[Expr]

  /** ''seq'' ::= ''seqsub,,1,,'' `,` ''seqsub,,2,,'' */
  def seq: Parser[Expr] = binaryLeft(seqBop, seqsub)

  override def start: Parser[Expr] = exprBlock
  override def expr: Parser[Expr] = seq
}

object ParserLike {

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

/** The parser for just bindings. */
object Parser extends UnitOpParser with ParserLike {
  override lazy val bop: OpPrecedence = Nil
  override def seqsub: Parser[Expr] = binary
}
