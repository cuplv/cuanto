package edu.colorado.plv.cuanto.jsy
package functions

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.binding.{Sequ, Undef}
import edu.colorado.plv.cuanto.jsy.functions.Parser.parse
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors

/**
  * @author Bor-Yuh Evan Chang
  */
class FunctionsParserSpec extends CuantoSpec with ParserBehaviors {

  override lazy val positives = Table(
    "concrete" -> "abstract",
    "(x) => x" -> Fun(None, Var("x") -> None :: Nil, None, Var("x")),
    "(x: any) => x" -> Fun(None, Var("x") -> Some(TAny) :: Nil, None, Var("x")),
    "function (x) { return x }" -> Fun(None, Var("x") -> None :: Nil, None, Sequ(Return(Var("x")), Undef))
  )

  "jsy.functions.Parser" should behave like parser(parse)
}
