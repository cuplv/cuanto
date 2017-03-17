package edu.colorado.plv.cuanto.sootloading

import org.scalatest.FlatSpec
import org.scalatest._
import soot.jimple.JimpleBody
import soot.shimple.ShimpleBody
import soot.{Body, Scene, SootClass, SootMethod}

import scala.collection.JavaConverters._


/**
  * Created by s on 3/17/17.
  */
class SootLoadingSpec extends FlatSpec with Matchers{

  "SootLoading" should "successfully load jimple" in {
    val testClassExists = (scene: Scene) =>
      scene.getClasses.asScala.exists((clazz: SootClass) => {
        clazz.getName == "Test1" &&
        clazz.getMethodByName("main") != null &&
          clazz.getMethodByName("main").getActiveBody.isInstanceOf[JimpleBody]
      }

      )
    val classesExist = SootLoading.getAnalysisResult[Boolean](
      List(getClass.getResource("/test_files/test1/").getPath),
      Some("Test1"),
      testClassExists
    )
    classesExist shouldBe a [Some[_]]
    classesExist.get shouldBe true
  }


}
