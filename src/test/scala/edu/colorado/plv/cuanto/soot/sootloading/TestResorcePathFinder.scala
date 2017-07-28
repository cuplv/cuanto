package edu.colorado.plv.cuanto.soot.sootloading

/**
  * Created by s on 7/28/17.
  */
object TestResorcePathFinder {
  val fileSep: String = System.getProperty("file.separator")
  def getJavaTestFile(name: String): String ={
    System.getProperty("user.dir") +
      fileSep + "src" +
      fileSep + "test" +
      fileSep + "resources" +
      fileSep + "test_files" +
      fileSep + name

  }

}
