package edu.colorado.plv.cuanto.jsy
package lambda

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.arithmetic._
import edu.colorado.plv.cuanto.jsy.binding._
import edu.colorado.plv.cuanto.jsy.functions._
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors

/**
  * @author Bor-Yuh Evan Chang
  */
class LambdaParserSpec extends CuantoSpec with ParserBehaviors {

  override lazy val positives = Table(
    "concrete" -> "abstract",
    """{ let inc = i => i + 1
      |  inc(3) }
    """.stripMargin
    ->
    Bind(MConst, Var("inc"),
      Fun(None, List(Var("i") -> None), None, Plus(Var("i"), N(1))),
      Call(Var("inc"), List(N(3)))
    )
  )

  "jsy.lambda.Parser" should behave like parser(Parser.parse)

}
