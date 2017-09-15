package edu.colorado.plv.cuanto.javita

import edu.colorado.plv.cuanto.CuantoSpec

/**
  * @author Sergio Mover
  */
class TestJavitaSemantic extends CuantoSpec {
  import edu.colorado.plv.cuanto.javita.Expressions

  def runMethods[A](table : org.scalatest.prop.TableFor2[() => A, A]) : Unit = {
    it should s"Compute correct result" in {
      forAll (table) { (e, n) => {
        (e()) should equal (n)
      }}
    }
  }

  behavior of "Arithmetic operators"
  val exprTests = Table(
    ("Method call","Result"),
    (Expressions.testExpr1 _,3),
    (Expressions.testExpr2 _, 13),
    (Expressions.testExpr3 _, 1),
    (Expressions.testExpr4 _, 6),
    (Expressions.testExpr5 _, 5),
    (Expressions.testExpr6 _, 2),
    (Expressions.testExpr7 _, 5),
    (Expressions.testExpr8 _, 9),
    (Expressions.testExpr9 _, 4),
    (Expressions.testExpr10 _, 1),
    (Expressions.testExpr11 _, 6),
    (Expressions.testExpr12 _, 3),
    (Expressions.testExpr13 _, 0)
  )
  runMethods(exprTests)

  behavior of "Boolean operators"
  val boolTests = Table(
    ("Method call","Result"),
    (Expressions.testBool1 _, true),
    (Expressions.testBool2 _, false),
    (Expressions.testBool3 _, false),
    (Expressions.testBool4 _, false),
    (Expressions.testBool5 _, true),
    (Expressions.testBool6 _, true),
    (Expressions.testBool7 _, true),
    (Expressions.testBool8 _, false),
    (Expressions.testBool9 _, false),
    (Expressions.testBool10 _, true)
  )
  runMethods(boolTests)

  behavior of "Comparison operators"
  val compTests = Table(
    ("Method call","Result"),
    (Expressions.testComparisons1 _, true),
    (Expressions.testComparisons2 _, true),
    (Expressions.testComparisons3 _, false)
  )
  runMethods(compTests)

}
