package edu.colorado.plv.cuanto.jimpleinterpreter

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Lumber on 3/19/2017.
  */
class JimpleInterpreterSpec extends FlatSpec with Matchers {
    val fileSep: String = System.getProperty("file.separator")
    val pathSep: String = System.getProperty("path.separator")

    val testFilePaths: String = System.getProperty("user.dir") + fileSep + "src" + fileSep + "test" + fileSep + "resources" + fileSep + "test_files" + fileSep + "test1"
    SootLoading.init(List(testFilePaths), Some("Test1"))

    "Jimple interpreter" should "concretely and correctly interpret the Jimple code" in {}

    def getVariableVal(name: String): Int = {
        SootLoading.memory.foreach {
            case (namep, valuep) => if (name == namep.getName) return valuep
        }
        0
    }

    it should "" in {
        getVariableVal("i4") shouldEqual 10
        getVariableVal("z0") shouldEqual 1
        getVariableVal("$i1") shouldEqual 8
        getVariableVal("$i2") shouldEqual 2097152
        getVariableVal("b0") shouldEqual 10
        getVariableVal("i3") shouldEqual 1048576
    }
}
