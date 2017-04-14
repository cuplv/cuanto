package edu.colorado.plv.cuanto.jimpleinterpreter

import org.scalatest.{FlatSpec, Matchers}
import soot.Local

/**
  * Created by Lumber on 3/19/2017.
  */
class JimpleInterpreterSpec extends FlatSpec with Matchers {
  val fileSep: String = System.getProperty("file.separator")
  val pathSep: String = System.getProperty("path.separator")

  val testFilePaths: String = System.getProperty("user.dir") + fileSep + "src" + fileSep + "test" + fileSep + "resources" + fileSep + "test_files" + fileSep + "test1"

  val memory: MutableMemory = Driver.run(List(testFilePaths), Some("Test1"))

  def getVariableVal(name: String, memory: MutableMemory): Int = {
    memory.getCurrentStack.foreach {
      case (namep, valuep) =>
        namep match {
          case l: Local => if (name == l.getName) return valuep
          case _ => ???
        }
    }
    -1
  }

  "Jimple interpreter" should "concretely and correctly interpret the Jimple code" in {}

  it should "" in {
    getVariableVal("i0", memory) shouldEqual 1048576
    getVariableVal("i1", memory) shouldEqual 55
    getVariableVal("i2", memory) shouldEqual 1048576
  }
}
