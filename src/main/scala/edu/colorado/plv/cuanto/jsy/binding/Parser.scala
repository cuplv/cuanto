package edu.colorado.plv.cuanto.jsy
package binding

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse variables and bindings.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  import OpParserLike._

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



/** The parser for just bindings. */
object Parser extends UnitOpParser with ParserLike {
  override lazy val bop: OpPrecedence = Nil
  override def seqsub: Parser[Expr] = binary
}
