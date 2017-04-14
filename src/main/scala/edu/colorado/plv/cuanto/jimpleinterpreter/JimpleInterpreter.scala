package edu.colorado.plv.cuanto.jimpleinterpreter

import java.util

import soot.jimple._
import soot.{Body, Local, Scene, SceneTransformer}

/**
  * Created by Lumber on 3/19/2017.
  */
class JimpleInterpreter() extends SceneTransformer {
  val DEBUG = true
  var pc: Stmt = _

  val memory = new MutableMemory

  override def internalTransform(s: String, map: util.Map[String, String]): Unit = {
    val mainMethod = Scene.v().getMainMethod

    memory.createNewStack()
    concreteInterpret(mainMethod, memory, List(0))
    assert(memory.stackDepth == 1, "Stack depth is: " + memory.stackDepth)

    if (DEBUG) {
      // println(mainMethod.releaseActiveBody()) // why would this lead to exception?
      memory.getCurrentStack.foreach {
        case (variable, value) => println(variable + ": " + value)
      }
    }
  }
}
