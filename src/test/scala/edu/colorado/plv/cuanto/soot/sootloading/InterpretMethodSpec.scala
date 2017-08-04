package edu.colorado.plv.cuanto.soot.sootloading
import edu.colorado.plv.cuanto.soottest.EmptyMainTest
import edu.colorado.plv.cuanto.jutil.implicits._
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Success

/**
  * Created by Shawn Meier on 7/26/17.
  */
class InterpretMethodSpec extends FlatSpec with Matchers {
  val emptyMainTest = classOf[EmptyMainTest].getRelativeURL.get.getPath
//  "Interpreter" should "interpret empty class" in {
//    val result = Interpreter.interpretMethod("Test1", "void main(java.lang.String[])", List(TestResorcePathFinder.getJavaTestFile("Test1")))
//  }
  "Interpreter" should "interpret a method that returns 1" in {
    val result = InterpretMethod.interpretMethod("ArithmeticTest", "int test1()",
      List(TestResorcePathFinder.getJavaTestFile("InterpreterTests","ArithmeticTest")))
    result shouldBe Success(3)
  }
  "Interpreter" should "handle a local integer variable" in {
    val result = InterpretMethod.interpretMethod("ArithmeticTest", "int test2()",
      List(TestResorcePathFinder.getJavaTestFile("InterpreterTests","ArithmeticTest")))
    result shouldBe Success(4)
  }
}
