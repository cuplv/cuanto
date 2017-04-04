package edu.colorado.plv.cuanto.jsy
package binding

import edu.colorado.plv.cuanto.jsy.binding.Parser.parse
import edu.colorado.plv.cuanto.testing.implicits.tryEquality
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Kyle Headley
  * @author Bor-Yuh Evan Chang
  */
class BindingParserSpec extends FlatSpec with Matchers with PropertyChecks {

  behavior of "jsy.binding.Parser"

  val positives = Table(
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

  val flexibles = Table(
    "concrete" -> "abstract",
    "{ let x = y y }"
      -> Bind(Var("x"), Var("y"), Var("y"))
  )

  val negatives = Table(
    "concrete",
    "123abc",
    "(let x = y)",
    "(x; y)"
  )

  forAll (positives) { (conc, abs) =>
    it should s"parse $conc into $abs" in {
      parse(conc) shouldEqual abs
    }
  }

  forAll (flexibles) { (conc, abs) =>
    it should s"flexibly parse $conc into $abs" in {
      parse(conc) shouldEqual abs
    }
  }

  forAll (negatives) { conc =>
    it should s"not parse $conc" in {
      parse(conc) should be a 'failure
    }
  }

}
