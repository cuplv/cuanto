package edu.colorado.plv.cuanto.scoot
package arithmetic

import edu.colorado.plv.cuanto.scoot.jimple._
import scala.collection.immutable.HashMap

/** Implement an interpreter for sequences of Jimple assignment
  * statements that represent integer arithmetic programs
  */
object Interpreter {

  /** An "execution environment" or state, mapping variables (of type
    * `Local`) to integer values */
  type Env = Map[String,Int]

  /** An environment with no assigned variables */
  val emptyEnv: Env = new HashMap[String,Int]()

  /** Interpret arithmetic expressions encoded as a single `Value` */
  def denote(v: Value, env: Env = emptyEnv): Option[Int] = v match {
    case Local(s) => env get s
    case IntConstant(v) => Some(v)
    case AddExpr(e1, e2) => for {
      arg1 <- denote(e1, env)
      arg2 <- denote(e2, env)
    } yield arg1 + arg2
    case SubExpr(e1, e2) => for {
      arg1 <- denote(e1, env)
      arg2 <- denote(e2, env)
    } yield arg1 - arg2
    case MulExpr(e1, e2) => for {
      arg1 <- denote(e1, env)
      arg2 <- denote(e2, env)
    } yield arg1 * arg2
    case DivExpr(e1, e2) => for {
      arg1 <- denote(e1, env)
      arg2 <- denote(e2, env)
    } yield arg1 / arg2
    case NegExpr(e) => for {
      arg <- denote(e, env)
    } yield -arg
  }
}
