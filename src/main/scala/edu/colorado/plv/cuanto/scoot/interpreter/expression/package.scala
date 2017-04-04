package edu.colorado.plv.cuanto.scoot.interpreter

import scala.collection.immutable.{Map, HashMap}
import soot._

package expression {
  trait Result
  case class IntR(i: Int) extends Result
}

package object expression {
  type Env = Map[String,Result]
  type RFun = Env => Option[Result]

  val emptyEnv: Env = new HashMap[String,Result]()

  def interpret(v: Value, env: Env = emptyEnv): Option[Result] = ???
}
