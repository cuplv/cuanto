package edu.colorado.plv.cuanto.jsy
package imp

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.arithmetic.N
import edu.colorado.plv.cuanto.jsy.binding._
import edu.colorado.plv.cuanto.jsy.boolean._
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors
import edu.colorado.plv.cuanto.jsy.mutation.Assign

/**
  * @author Bor-Yuh Evan Chang
  */
class ImpParserSpec extends CuantoSpec with ParserBehaviors {

  override lazy val positives = Table(
    "concrete" -> "abstract",
    """{ let x = 3
      |  var y = true
      |  if (y) { x = 4 } else { x = 5 }
      |}
    """.stripMargin
      -> Bind(MVar, Var("x"), N(3),
        Bind(MVar, Var("y"), B(true),
          If(Var("y"),
            Binary(Assign, Var("x"), N(4)),
            Binary(Assign, Var("x"), N(4))
          )
        )
      )

  )

  "jsy.imp.Parser" should behave like parser(Parser.parse)
}