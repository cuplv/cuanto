package edu.colorado.plv.cuanto.scoot.interpreter

import scala.collection.immutable.{Map, HashMap}
import soot._

/** Interpreter for expressions (soot Values).  This depends on
  * expression evaluation not having side-effects.
  */
package expression {

  /** A sum type of possible types that an expression can evaluate to */
  trait Result
  case class IntR(i: Int) extends Result
}

package object expression {

  /** Evaluation environment */
  type Env = Map[String,Result]
  type RFun = Env => Option[Result]

  /** The empty environment */
  val emptyEnv: Env = new HashMap[String,Result]()

  /** Interpret a value under a particular environment */
  def interpret(v: Value, env: Env = emptyEnv): Option[Result] = (for {
    rfun <- interpR(v)
  } yield rfun(env)).flatten

  private def interpR(v: Value): Option[RFun] =
    anyOf(Seq(Arithmetic.interpNode(v)(interpR), Locals.interpNode(v)(interpR)))

  private def anyOf(is: Traversable[Option[RFun]]): Option[RFun] =
    is.flatten.headOption

  /** Sub-interpreter for looking up Local values in the environment */
  object Locals {
    /** Interpret a Local node */
    def interpNode(v: Value)(r: Value => Option[RFun]):
        Option[RFun] = v match {
      case v: Local => Some(_ get v.getName())
      case _ => None
    }
  }
}
