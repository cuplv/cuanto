package edu.colorado.plv.cuanto.jsy
package binding

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, ExprParserLike, UnitExprParser}

/** Parse variables and bindings.
  *
  * @author Kyle Headley
  */
trait ParserLike extends ExprParserLike with JsyParserLike {

  /** ''variable'' := ''id'' */
  def variable: Parser[Expr] =
    positioned {
      """[a-zA-Z_]*""".r ^^ (s => Var(s))
    } | failure("expected a variable")


  /** ''expratom'' ::= ''variable'' | 'Let' ''expr'' '=' ''expr'' 'in' ''expr'' */
  abstract override def expratom: Parser[Expr] =
    positioned {
      "Let" ~> expr ~ ("=" ~> expr <~ "in") ~ expr ^^ { case v ~ e1 ~ e2 => Bind(v, e1, e2) }
    } | variable
}

/** The parser for just bindings. */
object Parser extends UnitExprParser with ParserLike {
  override def start: Parser[Expr] = expr
}
