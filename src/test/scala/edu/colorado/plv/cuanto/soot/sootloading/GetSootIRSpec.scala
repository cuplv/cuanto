package edu.colorado.plv.cuanto.soot.sootloading

import edu.colorado.plv.cuanto.soot.sootloading.GetSootIR.getClass
import org.scalatest.{FlatSpec, Matchers}
import soot.{Scene, SootClass}
import soot.util.Chain

import scala.collection.JavaConverters._


/**
  * Created by s on 6/9/17.
  */
class GetSootIRSpec extends FlatSpec with Matchers {
  "GetSootIR" should "get intermediate representation" in {
    val scene: Scene = GetSootIR.initSootTest(List("/Users/s/Documents/source/cuanto/test_files/test1"))

    val classes = scene.getClasses().toArray
    classes.map(a => {
      if(a.toString().contains("Test1")){
        println()
      }
    })



    val testClass = scene.getSootClass("Test1")
    val methods = testClass.getMethods()
    methods.size() should equal (1)

  }

}
