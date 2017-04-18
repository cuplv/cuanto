package edu.colorado.plv.cuanto.scoot.interpreter
package mutation

import soot._
import soot.jimple._

import expression._

/** Interpreter for soot Units that modify the evaluation
  * environment */
package object mutation {

  private def some[A](a: A): Option[A] = Some(a)

  /** Step an environment forward over a single statement */
  def step(stmt: AssignStmt)(env: Env): Option[Env] = {
    val varNameO: Option[Local] = stmt.getLeftOp() match {
      case l: Local => Some(l)
      case _ => None
    }
    val newValueO: Option[R] = expression.interpret(stmt.getRightOp(), env)
    for {
      varName <- varNameO
      newValue <- newValueO
    } yield env + (varName.getName() -> newValue)
  }

  /** Interpret the integer value of a variable mutated over a sequence
    * of assignment statements */
  def interpret(ss: Traversable[AssignStmt], v: String): Option[R] =
    interpret(ss).flatMap(_ get v)

  def interpret(ss: Traversable[AssignStmt]): Option[Env] =
    ss.foldLeft(some(emptyEnv))((env,stmt) => env.flatMap(step(stmt)))
}
