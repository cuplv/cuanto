package edu.colorado.plv.cuanto.sootloading

import java.util

import soot.{Scene, SceneTransformer}

/**
  * Created by s on 3/17/17.
  */
class GetJimple extends SceneTransformer {
  var scene : Option[Scene] = None

  override def internalTransform(s: String, map: util.Map[String, String]): Unit = {
    scene = Some(Scene.v())
  }
  def getScene(): Scene ={
    scene.getOrElse(throw new IllegalArgumentException("Scene not found"))
  }
}
