package edu.colorado.plv.cuanto
package scoot.interpreter

import scala.collection.immutable.{Map, HashMap}
import soot._

import scoot.domains._

package object expression {
  type R = Result[IntDom]
  type Env = Map[String,R]
  type RFun = Env => Option[R]

  val emptyEnv: Env = new HashMap[String,R]()

  def interpret(v: Value, env: Env = emptyEnv): Option[R] = (for {
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
