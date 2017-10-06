package edu.colorado.plv.cuanto.scoot.concrete_interpreter

import edu.colorado.plv.cuanto.CuantoSpec
import edu.colorado.plv.cuanto.soot.sootloading.InterpretMethod
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Shawn Meier
  *         Created on 9/1/17.
  */
class InterpretBodySpec extends CuantoSpec with InterpretBodyBehavior{
  behavior of "interpret"

  it should behave like interpreter(InterpretMethod.interpretMethod)
}
