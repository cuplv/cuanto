package edu.colorado.plv.cuanto.soot.sootloading

import edu.colorado.plv.cuanto.soot.sootloading.GetSootIR.getClass
import org.scalatest.{FlatSpec, Matchers}
import soot.jimple.JimpleBody
import soot.{Scene, SootClass}
import soot.util.Chain

import scala.collection.JavaConverters._


/**
  * Created by s on 6/9/17.
  */
class GetSootIRSpec extends FlatSpec with Matchers {
  "GetSootIR" should "load a set of non empty classes" in {
    val scene: Scene = GetSootIR.getSootIRFromDirectoryOfClassFiles(List("/Users/s/Documents/source/cuanto/test_files/test1/bin/"),None)

    val classes = scene.getClasses().toArray
    assert(classes.length > 0)

  }
  "GetSootIR" should "load a specific class" in {
    val scene: Scene = GetSootIR.getSootIRFromDirectoryOfClassFiles(List("/Users/s/Documents/source/cuanto/test_files/test1/bin/"),Some("Test1"))
//    val classes = scene.getClasses().toArray()
    scene.getSootClass("Test1").isPhantom() shouldBe false
  }
  "GetSootIR" should "load jimple bytecode" in{
    val scene: Scene = GetSootIR.getSootIRFromDirectoryOfClassFiles(List("/Users/s/Documents/source/cuanto/test_files/test1/bin/"),Some("Test1"))
    val methods = scene.getSootClass("Test1").getMethods()
    val method = scene.getSootClass("Test1").getMethod("void main(java.lang.String[])")
    methods.size() shouldBe 2
    method.getActiveBody shouldBe a[JimpleBody]
  }

}
