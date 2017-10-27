package edu.colorado.plv.cuanto.scoot.sootloading

import edu.colorado.plv.cuanto.wala.Client
import org.scalatest.FlatSpec
import org.scalatest._
import org.scalatest.tagobjects.Slow
import soot.jimple.JimpleBody
import soot.shimple.ShimpleBody
import soot.{Body, Scene, SootClass, SootMethod}

import scala.collection.JavaConverters._
import scala.util.Success
import edu.colorado.plv.cuanto.walatest.EmptyMainTest
import edu.colorado.plv.cuanto.jutil.implicits._


/**
  * @author Shawn Meier
  *         Created on 3/17/17.
  */
class SootLoadingSpec extends FlatSpec with Matchers {
  val fileSep: String = System.getProperty("file.separator")
  val pathSep: String = System.getProperty("path.separator")
  lazy val walaTest = classOf[EmptyMainTest].getRelativeURL.get.getPath

  /**
    * Test checks whether a sample program loads
    */
  "SootLoading" should "successfully load test file" taggedAs(Slow) in {
    val testClassExists = (scene: Scene) =>
      scene.getClasses.asScala.exists((clazz: SootClass) => {
        clazz.getName == "edu.colorado.plv.cuanto.walatest.EmptyMainTest" &&
          clazz.getMethodByName("main") != null
      }

      )

    println(walaTest)

    val classesExist = SootLoading.getAnalysisResult[Boolean](
      // List(getClass.getResource("/test_files/test1/").getPath),
      List(walaTest),
      Some("edu.colorado.plv.cuanto.walatest.EmptyMainTest"),
      testClassExists
    )
    classesExist shouldBe a[Success[_]]
    classesExist.get shouldBe true
  }

  /**
    * Test that the jimple body exists when the loading finishes
    */
  "SootLoading" should "successfully load jimple" taggedAs(Slow) in {
    val testClassExists = (scene: Scene) =>
      scene.getClasses.asScala.exists((clazz: SootClass) => {
        clazz.getName == "edu.colorado.plv.cuanto.walatest.EmptyMainTest" &&
          clazz.getMethodByName("main") != null &&
          clazz.getMethodByName("main").getActiveBody.isInstanceOf[JimpleBody]
      }

      )

    val classesExist = SootLoading.getAnalysisResult[Boolean](
      // List(getClass.getResource("/test_files/test1/").getPath),
      List(walaTest),
      Some("edu.colorado.plv.cuanto.walatest.EmptyMainTest"),
      testClassExists
    )
    classesExist shouldBe a[Success[_]]
    classesExist.get shouldBe true
  }

}

