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
    "test1" -> 3,
    "test2" -> 4,
    "test3" -> 5,
    "test4" -> 5,
    "test5" -> 4,
    "test6" -> 5,
    "test7" -> 4,
    "test8" -> 5,
    "test9" -> 4,
    "test10" -> 3,
    "test11" -> 2,
    "test12" -> 2
  )
  def interpreter(interpret : (String,String,List[String]) => Try[CValue]) = {
    forAll(interpretBodyTests){ (test, result) =>
      it should s"interpret test $test to $result" in {
        val res = interpret("ArithmeticTest", s"int $test()",
          List(TestResorcePathFinder.getJavaTestFile("InterpreterTests","ArithmeticTest")))
        res match{
          case Failure(e) => throw e
          case _ => ()
        }
        res shouldBe Success(CInteger(result))
      }
    }
  }
}
