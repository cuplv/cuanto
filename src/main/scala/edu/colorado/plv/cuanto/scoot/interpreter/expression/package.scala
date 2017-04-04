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

  def interpret(v: Value, env: Env = emptyEnv): Option[Result] = (for {
    rfun <- interpR(v)
  } yield rfun(env)).flatten

  def interpR(v: Value): Option[RFun] =
    anyOf(Seq(Arithmetic.interpNode(v)(interpR), Locals.interpNode(v)(interpR)))

  def anyOf(is: Traversable[Option[RFun]]): Option[RFun] =
    is.flatten.headOption

  object Locals {
    def interpNode(v: Value)(r: Value => Option[RFun]):
        Option[RFun] = v match {
      case v: Local => Some(_ get v.getName())
      case _ => None
    }
  }
}
