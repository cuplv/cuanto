package edu.colorado.plv.cuanto.wala

import org.scalatest.FlatSpec
import edu.colorado.plv.cuanto.jutil.implicits._
import edu.colorado.plv.cuanto.walatest.EmptyMainTest

/**
  * @author Bor-Yuh Evan Chang
  */
class ClientSpec extends FlatSpec {
  import edu.colorado.plv.cuanto.wala._

  "apply" should "not crash" in {
    val url = classOf[EmptyMainTest].getRelativeURL.get
    Client(url)
  }

  lazy val walaTest = Client(classOf[EmptyMainTest].getRelativeURL.get)

  "classHierarchy" should "not crash" in {
    walaTest.classHierarchy
  }

  "applicationClasses" should "get the right number of application classes" in {
    assertResult(1) { walaTest.applicationClasses.size }
  }

  "makeIR" should "get the right number of methods" in {
    assertResult(2) { walaTest.makeIR(walaTest.applicationClasses).size }
  }

  it should "get the right number of main methods" in {
    assertResult(1) { walaTest.makeIR(walaTest.applicationClasses, Client.MainSelector).size }
  }

}
