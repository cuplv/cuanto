package edu.colorado.plv.cuanto.numdomain

import edu.colorado.plv.cuanto.numdomain.apron.ApronTest
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by lumber on 4/20/17.
  */
class NumDomainSpec extends FlatSpec with Matchers {

  /**
    * java.library.path specifies the directories where System.loadLibrary() looks for the dynamic library file.
    * If you change the java.library.path system property in your code, it will not have any effect.
    * There are hacks to make Java "forget" the initial value and re-evaluate the contents of the java.library.path system property.
    *
    * However, the dependent library is not loaded by Java, it's loaded by Windows.
    * Windows does not care about java.library.path, it only cares about the PATH environment variable. Your only option is to adjust PATH for your Java process.
    * For example, if you start it from a batch file, change the PATH environment variable right before the java invocation.
    *
    *
    * http://stackoverflow.com/questions/12566732/java-jni-and-dependent-libraries-on-windows
    */

  /**
    * As a result, I set the current working directory to "/Users/lumber/Documents/workspace/cuanto/lib" in "Run -> Edit Configurations..."
    * However, this is only a temporary solution.
    */

  ApronTest.runInterfaceTest()
}
