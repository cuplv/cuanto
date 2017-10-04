package edu.colorado.plv.cuanto.scoot.concrete_interpreter

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.jsy.arithmetic.implicits
import edu.colorado.plv.cuanto.soot.sootloading.TestResorcePathFinder

import scala.util.{Failure, Success, Try}

/**
  * @author Shawn Meier
  *         Created on 9/1/17.
  */
trait InterpretBodyBehavior { self: CuantoSpec =>
  import edu.colorado.plv.cuanto.testing.implicits.tryEquality
  val interpretBodyTests = Table(
    "methodname" -> "result",
    ("ArithmeticTest","int test1") -> CInteger(3),
    ("ArithmeticTest","int test2") -> CInteger(4),
    ("ArithmeticTest","int test3") -> CInteger(5),
    ("ArithmeticTest","int test4") -> CInteger(5),
    ("ArithmeticTest","int test5") -> CInteger(4),
    ("ArithmeticTest","int test6") -> CInteger(5),
    ("ArithmeticTest","int test7") -> CInteger(4),
    ("ArithmeticTest","int test8") -> CInteger(5),
    ("ArithmeticTest","int test9") -> CInteger(4),
    ("ArithmeticTest","int test10") -> CInteger(3),
    ("ArithmeticTest","int test11") -> CInteger(2),
    ("ArithmeticTest","int test12") -> CInteger(2),
    ("BooleanTest", "boolean test1") -> CInteger(1),
    ("BooleanTest", "boolean test2") -> CInteger(1),
    ("BooleanTest", "boolean test3") -> CInteger(0)
  )
  val interpretBoolBodyTests = Table(
    "methodname" -> "result",
    "test1" -> true
  )
  def interpreter(interpret : (String,String,List[String]) => Try[CValue]) = {
    forAll(interpretBodyTests){ (test, result) =>
      it should s"interpret test $test to $result" in {
        val methodName = test._2
        val className = test._1
        val testPath = TestResorcePathFinder.getJavaTestFile("InterpreterTests", className)
        val res = interpret(className, s"$methodName()",
          List(testPath))
        res match{
          case Failure(e) => throw e
          case _ => res shouldBe Success(result)
        }
      }
    }
  }
}
