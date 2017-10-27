package edu.colorado.plv.cuanto.jsy
package mutation

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike}

trait ParserLike extends OpParserLike with JsyParserLike {
  abstract override def opAtom: Parser[Expr] =
    positioned("null" ^^^ Null) |
    super.opAtom

  lazy val mutationBop: OpPrecedence = List(
    List("=" -> Assign)
  )
}

