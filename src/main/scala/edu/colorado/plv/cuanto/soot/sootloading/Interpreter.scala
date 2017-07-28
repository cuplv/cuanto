package edu.colorado.plv.cuanto.soot.sootloading

import edu.colorado.plv.cuanto.scoot.sootloading.SootLoading

import soot.Scene

import scala.util.Try
import scala.collection.JavaConverters._


/**
  * Created by s on 7/21/17.
  */
object Interpreter {
//  lazy val emptyTest = classOf[EmptyMainTest].getRelativeURL.get
  def interpretMethod(classname: String, methodName: String, paths: List[String]): Try[Integer] = {
    SootLoading.getAnalysisResult(paths, None, getInterpreter(classname, methodName))
  }
  def getInterpreter(className: String, methodName: String): Scene => Integer = {
    (scene: Scene) => {
      val results: Iterable[Integer] = scene.getClasses.asScala.flatMap(clazz =>{
        if(clazz.getName == className) {
          val method = clazz.getMethod(methodName)
          ???
        }else{None}
      })
      val lres = results.toList
      if (lres.size == 1){
        lres(0)
      }else{
        throw new ClassNotFoundException()
      }
    }
  }

}