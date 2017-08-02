package edu.colorado.plv.cuanto.soot.sootloading
import edu.colorado.plv.cuanto.soottest.EmptyMainTest
import edu.colorado.plv.cuanto.jutil.implicits._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Shawn Meier on 7/26/17.
  */
class InterpreterSpec extends FlatSpec with Matchers {
  val emptyMainTest = classOf[EmptyMainTest].getRelativeURL.get.getPath
//  "Interpreter" should "interpret empty class" in {
//    val result = Interpreter.interpretMethod("Test1", "void main(java.lang.String[])", List(TestResorcePathFinder.getJavaTestFile("Test1")))
//  }
  "Interpreter" should "interpret a method that returns 1" in {
    val result = Interpreter.interpretMethod("ArithmeticTest", "int test1()",
      List(TestResorcePathFinder.getJavaTestFile("InterpreterTests","ArithmeticTest")))
    result shouldBe 1
  }
}
