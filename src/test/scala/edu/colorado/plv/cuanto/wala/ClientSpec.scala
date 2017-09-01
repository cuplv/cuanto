package edu.colorado.plv.cuanto.wala

import org.scalatest.FlatSpec

/**
  * @author Bor-Yuh Evan Chang
  */
class ClientSpec extends FlatSpec {
  import edu.colorado.plv.cuanto.tests.Walatest

  "apply" should "not crash" in {
    Client(Walatest.emptyMainTestURL)
  }

  lazy val walaTest = Client(Walatest.emptyMainTestURL)

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
