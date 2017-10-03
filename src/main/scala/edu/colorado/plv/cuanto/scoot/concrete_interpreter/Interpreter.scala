package edu.colorado.plv.cuanto.scoot
package concrete_interpreter

import edu.colorado.plv.cuanto.scoot.jimple._
import soot.jimple.internal.{JAssignStmt, JReturnStmt, JimpleLocal}
//import edu.colorado.plv.cuanto.scoot.concrete_interpreter.ConcreteMemory.{CValue,CInteger}

import scala.collection.immutable.HashMap
import scala.util.{Success, Try}

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
  def interpretBody(b : Body): Try[CValue] = {
    Try(internal_interpretBody(List(emptyEnv), b.getFirstNonIdentityStmt, b).getOrElse(throw new RuntimeException("interpreter exception")))
  }
  def updateEnv(env: List[Env], varname: Value, value: Int): List[Env] = {

    val newEnv: List[Env] = varname match{
      case Local(n) =>  {
        List(env.head + ((n, value))) ::: env.tail
      }
      case _ => {
        ???
      }
    }
    newEnv
  }
  def internal_interpretBody(env : List[Env], loc: Stmt, b : Body): Option[CValue] = {
    val successor: Set[Stmt] = b.getSuccessors(loc) // TODO: wrap jimple body b.getUnits.getSuccOf(loc)
    loc match {
      case ReturnStmt(op)  => denote(op, env.head).map(a => CInteger(a))
//      case a: AssignStmt => {
//        val varname: soot.Value = a.getLeftOp
//        val newEnv = updateEnv(env, varname, denote(a.getRightOp, env.head).get)
//        internal_interpretBody(newEnv, successor, b)
//      }
      case AssignStmt(lval, rval) => {
        val varname = lval
        val newEnv = updateEnv(env, varname, denote(rval, env.head).get)
        if(successor.size == 1)
          internal_interpretBody(newEnv, successor.iterator.next(), b)
        else
          ??? //malformed
      }
      case _ => {
        ???
      }
    }
  }
}
