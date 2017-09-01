package edu.colorado.plv.cuanto.wala

import org.scalatest.FlatSpec

/**
  * @author Bor-Yuh Evan Chang
  */
class ClientSpec extends FlatSpec {
  import edu.colorado.plv.cuanto.tests.Walatest

  lazy val emptyMainTest = Client(Walatest.emptyMainTestURL)

  "classHierarchy" should "not crash" in {
    emptyMainTest.classHierarchy
  }

  "applicationClasses" should "get the right number of application classes" in {
    assertResult(1) { emptyMainTest.applicationClasses.size }
  }

  "makeIR" should "get the right number of methods" in {
    assertResult(2) { emptyMainTest.makeIR(emptyMainTest.applicationClasses).size }
  }

  it should "get the right number of main methods" in {
    assertResult(1) { emptyMainTest.makeIR(emptyMainTest.applicationClasses, Client.MainSelector).size }
  }

}
