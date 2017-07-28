package edu.colorado.plv.cuanto.soot.sootloading
import edu.colorado.plv.cuanto.soottest.EmptyMainTest
import edu.colorado.plv.cuanto.jutil.implicits._
import org.scalatest.FlatSpec

/**
  * Created by Shawn Meier on 7/26/17.
  */
class InterpreterSpec extends FlatSpec {
  val emptyMainTest = classOf[EmptyMainTest].getRelativeURL.get.getPath
  "Interpreter" should "interpret empty class" in {
    val result = Interpreter.interpretMethod("Test1", "void main(java.lang.String[])", List(TestResorcePathFinder.getJavaTestFile("Test1")))
    println(result)
  }
}
