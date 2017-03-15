package edu.colorado.plv.cuanto.jsy
package numerical

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike}

/** Parse into the numerical sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  lazy val comparisonBop: OpPrecedence = List(
    /* lowest */
    List("===" -> Eq, "!==" -> Ne),
    List("<" -> Lt, "<=" -> Le, ">" -> Gt, ">=" -> Ge)
    /* highest */
  )
}

/** The parser for just this arithmetic sub-language. */
object Parser
