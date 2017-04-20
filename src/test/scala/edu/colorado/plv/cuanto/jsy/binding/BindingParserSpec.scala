package edu.colorado.plv.cuanto.jsy
package binding

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.binding.Parser.parse
import edu.colorado.plv.cuanto.jsy.common.ParserBehaviors

/**
  * @author Kyle Headley
  * @author Bor-Yuh Evan Chang
  */
class BindingParserSpec extends CuantoSpec with ParserBehaviors {

  override lazy val positives = Table(
    "concrete" -> "abstract",
    // expressions
    "x" -> Var("x"),
    "two" -> Var("two"),
    "abc123" -> Var("abc123"),
    "let x = two; x"
      -> Bind(Var("x"), Var("two"), Var("x")),
    "undefined" -> Unit,
    "undefined, undefined" -> Binary(Seq, Unit, Unit),
    // blocks
    "{ x }" -> Var("x"),
    "{ let x = two; x }"
      -> Bind(Var("x"), Var("two"), Var("x")),
    // declarations
    "let x = two"
      -> Bind(Var("x"), Var("two"), Unit),
    "let x = two; let y = three"
      -> Bind(Var("x"), Var("two"),
           Bind(Var("y"), Var("three"), Unit)
         ),
    // statements
    "x; y" -> Binary(Seq, Var("x"), Var("y"))
  )

  override lazy val flexibles = Table(
    "concrete" -> "abstract",
    "{ let x = y y }"
      -> Bind(Var("x"), Var("y"), Var("y"))
  )

  override lazy val negatives = Table(
    "concrete",
    "123abc",
    "(let x = y)",
    "(x; y)"
  )

  "jsy.binding.Parser" should behave like parser(parse)

}
