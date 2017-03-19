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
}
