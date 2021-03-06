package edu.colorado.plv.cuanto.scoot.sootloading

import java.util

import soot.{Scene, SceneTransformer}
import scala.collection.JavaConverters._

/**
  * Created by s on 3/17/17.
  * Used by SootLoading, do not directly instantiate.
  */
class GetJimple[T](analysis: Scene => T) extends SceneTransformer {
  var result: Option[T] = None

  override def internalTransform(s: String, map: util.Map[String, String]): Unit = {
    result = Some(analysis(Scene.v()))
    System.err.println(Scene.v().getApplicationClasses)
  }
}
