package edu.colorado.plv.cuanto.jsy.common

import edu.colorado.plv.cuanto.jsy._
import edu.colorado.plv.cuanto.parsing.RichParsers

import scala.util.parsing.combinator.RegexParsers

/** Parser components that handle expressions.
  *
  * @author Kyle Headley
  */
trait ExprParserLike extends RegexParsers with RichParsers {
  /** Define expressions for sub-parser use. */
  def expratom: Parser[Expr]

  /** ''expr'' ::= ''expratom'' | '(' ''expr'' ')' | '()' */
  // NOTE: the expratom case works, but the others don't. I don't know why
  def expr: Parser[Expr] =
    expratom | "(" ~> expr <~ ")" | "()" ^^ { _ => Unit} | failure("expected expression")
}
