package edu.colorado.plv.cuanto.wala

import org.scalatest.FlatSpec
import edu.colorado.plv.cuanto.jutil.implicits._

/**
  * @author Bor-Yuh Evan Chang
  */
class ClientSpec extends FlatSpec {
  import edu.colorado.plv.cuanto.tinyjava.ReturnFortytwoTest

  "apply" should "not crash" in {
    val url = classOf[ReturnFortytwoTest].getRelativeURL
    Client(url.get)
  }

  lazy val walaFortyTwo = Client(classOf[ReturnFortytwoTest].getRelativeURL.get)

  "classHierarchy" should "not crash" in {
    walaFortyTwo.classHierarchy
  }

  "applicationClasses" should "get the right number of application classes" in {
    assertResult(1) { walaFortyTwo.applicationClasses.size }
  }

  "makeIR" should "get the right number of methods" in {
    assertResult(2) { walaFortyTwo.makeIR(walaFortyTwo.applicationClasses).size }
  }

  it should "get the right number of main methods" in {
    assertResult(1) { walaFortyTwo.makeIR(walaFortyTwo.applicationClasses, Client.MainSelector).size }
  }

}
