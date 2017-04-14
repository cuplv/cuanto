package edu.colorado.plv.cuanto.jimpleinterpreter

import org.scalatest.{FlatSpec, Matchers}
import soot.Local

import scala.collection.immutable.HashMap

/**
  * Created by Lumber on 3/19/2017.
  */
class JimpleInterpreterSpec extends FlatSpec with Matchers {
    val fileSep: String = System.getProperty("file.separator")
    val pathSep: String = System.getProperty("path.separator")

    val testFilePaths: String = System.getProperty("user.dir") + fileSep + "src" + fileSep + "test" + fileSep + "resources" + fileSep + "test_files" + fileSep + "test1"
    PrepAnalysis.init(List(testFilePaths), Some("Test1"))

    println("********************")
    // "Jimple interpreter" should "concretely and correctly interpret the Jimple code" in {}

    /*def getVariableVal(name: String): Int = {
        res.foreach {
            case (namep, valuep) => if (name == namep.getName) return valuep
        }
        -1
    }*/

    /* it should "" in {
        getVariableVal("i4") shouldEqual 10
        getVariableVal("z0") shouldEqual 1
        getVariableVal("$i1") shouldEqual 8
        getVariableVal("$i2") shouldEqual 2097152
        getVariableVal("b0") shouldEqual 10
        getVariableVal("i3") shouldEqual 1048576
    }*/
}
