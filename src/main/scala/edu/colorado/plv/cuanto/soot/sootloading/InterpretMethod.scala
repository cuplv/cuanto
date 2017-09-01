package edu.colorado.plv.cuanto.soot.sootloading

import edu.colorado.plv.cuanto.scoot.concrete_interpreter.Interpreter
import edu.colorado.plv.cuanto.scoot.sootloading.SootLoading
import soot.Scene
import soot.jimple.JimpleBody

import scala.util.{Failure, Success, Try}
import scala.collection.JavaConverters._


/**
  * Created by Shawn Meier on 7/21/17.
  */
object InterpretMethod {
//  lazy val emptyTest = classOf[EmptyMainTest].getRelativeURL.get
  def interpretMethod(classname: String, methodName: String, paths: List[String]): Try[Int] = {
    SootLoading.getAnalysisResult(paths, Some(classname), getInterpreter(classname, methodName)) match{
      case Success(v) => v
      case _ => ???
    }
  }
  def getInterpreter(className: String, methodName: String): Scene => Try[Int] = {
    (scene: Scene) => {
      val results: Iterable[Try[Int]] = scene.getClasses.asScala.flatMap(clazz =>{
        if(clazz.getName == className) {
          val method = clazz.getMethod(methodName)
          val jimpleBody = method.getActiveBody
          jimpleBody match {
            case j : JimpleBody => Some(Interpreter.interpretBody(j))
            case _ => ???
          }
        }else{None}
      })
      val lres = results.toList
      if (lres.size == 1){
        lres(0)
      }else{
        Failure( new ClassNotFoundException())
      }
    }
  }

}