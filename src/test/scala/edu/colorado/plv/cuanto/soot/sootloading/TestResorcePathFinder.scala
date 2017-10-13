package edu.colorado.plv.cuanto.soot.sootloading
import soot.CompilationDeathException

import sys.process._

/**
  * Created by Shawn Meier on 7/28/17.
  */
object TestResorcePathFinder {
  val fileSep: String = System.getProperty("file.separator")
  def compileIfNotCompiled(path: String): Unit ={
    val command = s"javac ${path}.java"
    val res: Int = (command !)
    if(res != 0) {
      throw new CompilationDeathException(s"failed to compile test file ${path}")
    }
  }
  def getJavaTestFile(dirname: String, fileName: String): String ={
    val filePath: String = System.getProperty("user.dir") +
      fileSep + "src" +
      fileSep + "test" +
      fileSep + "resources" +
      fileSep + "test_files" +
      fileSep + dirname
//      fileSep + name
    compileIfNotCompiled(filePath + fileSep + fileName)
    filePath
  }

}
