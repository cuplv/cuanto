package edu.colorado.plv.cuanto.jsy
package functions

import edu.colorado.plv.cuanto.jsy.binding.Undef
import edu.colorado.plv.cuanto.jsy.common.UnitOpParser

import scala.util.parsing.input.Positional

/**
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends binding.ParserLike {

  def juxtaop: Parser[Expr => Expr] =
    callop

  def callop: Parser[Expr => Expr] =
    withpos("(" ~> repsep(seqsub, ",") <~ ")") ^^ { case (pos,args) =>
      { e0 => Call(e0, args) setPos pos }
    }

  def juxta: Parser[Expr] =
    atom ~ rep(juxtaop) ^^ { case e0 ~ ops =>
      ops.foldLeft(e0) { (acc, mk) => mk(acc) }
    }

  override def unarysub: Parser[Expr] = juxta

  def function: Parser[Expr] =
    arrow(expr, { (params, e: Expr) => Fun(None, params, None, e) }) |
    ("function" ~> opt(variable)) ~ parameters ~ opt(":" ~> typ) ~ functionBlock ^^ {
        case p ~ parameters ~ tann ~ e0 => Fun(p, parameters, tann, e0)
    }

  def functionBlock: Parser[Expr] =
    "{" ~> rep(stmt) <~ "}" ^^ reduceToExpr(Some(Undef))

  def functionTyp: Parser[Typ] =
    arrow(typ, TFun)

  def arrow[A,B <: Positional](q: => Parser[A], op: (Parameters,A) => B): Parser[B] =
    parameters ~ withpos("=>" ~> q) ^^ {
      case params ~ ((pos, t)) => op(params, t) setPos pos
    }

  def parameters: Parser[Parameters] =
    parenrepsep(pairoptsep(variable, ":", typ), ",")

  abstract override def opAtom: Parser[Expr] =
    function |
    super.opAtom

  abstract override def opTyp: Parser[Typ] =
    functionTyp |
    super.opTyp

  abstract override def uop: Parser[Uop] =
    "return" ^^^ Return |
    super.uop

}

/**
  * @author Bor-Yuh Evan Chang
  */
object Parser extends UnitOpParser with ParserLike {
  override lazy val bop: OpPrecedence = Nil
  override def seqsub: Parser[Expr] = binary
}


