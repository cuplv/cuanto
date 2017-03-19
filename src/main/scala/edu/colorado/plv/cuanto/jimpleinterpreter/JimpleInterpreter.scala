package edu.colorado.plv.cuanto.jimpleinterpreter

import java.util

import soot.{Scene, SceneTransformer}

import scala.collection.JavaConverters._
/**
  * Created by Lumber on 3/19/2017.
  */
class JimpleInterpreter extends SceneTransformer {
    override def internalTransform(s: String, map: util.Map[String, String]): Unit = {
        val it = Scene.v().getApplicationClasses.iterator()
        while (it.hasNext) {
            val klass = it.next()
            val itm = klass.methodIterator()
            while (itm.hasNext) {
                val method = itm.next()
                if (method.hasActiveBody) {
                    val methodBody = method.getActiveBody
                    val stmtIt = methodBody.getUnits.iterator()
                    while(stmtIt.hasNext) {
                        println(stmtIt.next())
                    }
                }
            }
        }
    }
}
