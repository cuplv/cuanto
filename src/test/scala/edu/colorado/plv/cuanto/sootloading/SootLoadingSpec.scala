package edu.colorado.plv.cuanto.sootloading

import org.scalatest.FlatSpec
import org.scalatest._
import soot.{Scene, SootClass}

import scala.collection.JavaConverters._


/**
  * Created by s on 3/17/17.
  */
class SootLoadingSpec extends FlatSpec with Matchers{
  "load" should "Load a java class file" in {
    //TODO: non hard coded path
    val l = SootLoading.load(List("/home/s/Documents/source/cuanto/test_files/test1/"),Some("Test1"))
    l shouldBe a [Some[_]]
    val test1ClassExists = l.get.getClasses.asScala.exists((clazz: SootClass) => clazz.getName == "Test1")
    test1ClassExists shouldBe true
  }

}
