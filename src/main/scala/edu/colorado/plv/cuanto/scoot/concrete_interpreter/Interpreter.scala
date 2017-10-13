package edu.colorado.plv.cuanto.scoot
package concrete_interpreter

import edu.colorado.plv.cuanto.scoot.jimple._

import scala.annotation.tailrec

import scala.collection.immutable.HashMap
import scala.util.Try

/** Implement an interpreter for sequences of Jimple assignment
  * statements that represent integer arithmetic programs
  */
object Interpreter {

  /** An "execution environment" or state, mapping variables (of type
    * `Local`) to integer values */
  type Env = Map[String,Int]

  /** An environment with no assigned variables */
  private val emptyEnv: Env = new HashMap[String,Int]() //TODO: update environment

  /** Interpret arithmetic expressions encoded as a single `Value` */
  def evaluate_expr(v: Value, env: Env = emptyEnv): Option[Int] = v match { //TODO: update denote
    case Local(s) => Some(env.getOrElse(s, throw new RuntimeException(s"Variable $s not found, malformed jimple")))
    case IntConstant(v) => {
      Some(v)
    }
    case AddExpr(e1, e2) => for {
      arg1 <- evaluate_expr(e1, env)
      arg2 <- evaluate_expr(e2, env)
    } yield arg1 + arg2
    case SubExpr(e1, e2) => for {
      arg1 <- evaluate_expr(e1, env)
      arg2 <- evaluate_expr(e2, env)
    } yield arg1 - arg2
    case MulExpr(e1, e2) => for {
      arg1 <- evaluate_expr(e1, env)
      arg2 <- evaluate_expr(e2, env)
    } yield arg1 * arg2
    case DivExpr(e1, e2) => for {
      arg1 <- evaluate_expr(e1, env)
      arg2 <- {val res = evaluate_expr(e2, env); if(res != 0) res else None}
    } yield arg1 / arg2
    case NegExpr(e) => for {
      arg <- evaluate_expr(e, env)
    } yield -arg
    case EqExpr(e1,e2) => for{
      arg1 <- evaluate_expr(e1,env)
      arg2 <- evaluate_expr(e2,env)
    } yield  if(arg1 == arg2) 1 else 0
    case NeExpr(e1,e2) => for{
        arg1 <- evaluate_expr(e1,env)
        arg2 <- evaluate_expr(e2,env)
      }yield if(arg1 != arg2) 1 else 0
    case _ => {
      ???
    }
  }
  def interpretBody(b : Body): Try[CValue] = {
    Try(internal_interpretBody(List(emptyEnv), b.getFirstNonIdentityStmt, b).getOrElse(throw new RuntimeException("interpreter exception")))
  }
  //TODO: update environment and update stack
  private def updateEnv(env: Env, varname: String, value: Int): Env = {
    env + (varname -> value)
  }
  private def malformedJimple(): Nothing = throw new RuntimeException("malformed jimple")
  @tailrec
  private def internal_interpretBody(stack : List[Env], loc: Stmt, b : Body): Option[CValue] = {
    //normal successor, conditional successor TODO: exceptional successor
    val successor = b.getSuccessors(loc)
    interpret_stmt(stack.head, loc) match {
      case InterpretNext(env) => internal_interpretBody(env :: stack.tail, successor._1.getOrElse(malformedJimple()), b)
      case InterpretConditionalJump(env) =>
        internal_interpretBody(env :: stack.tail,
          successor._2.getOrElse(malformedJimple()),b)
      case ReturnFromBody(returnValue) => returnValue
      case ExecutionExceptionDivideByZero(stmt) => ???
    }
  }

  /**
    * Data interpreter needs to understand how to continue interpretation after processing command
    */
  private trait StmtResult
  private trait NormalControlFlow extends StmtResult
  private sealed case class InterpretNext(newEnvironment : Env) extends NormalControlFlow
  private sealed case class InterpretConditionalJump(newEnvironment: Env) extends NormalControlFlow
  private sealed case class ReturnFromBody(result: Option[CValue])  extends NormalControlFlow
  //TODO: talk about design philosophy, I believe its easier to throw when we encounter malformed jimple
  //The design philosophy of java is that there are caught exceptions for places where you need to react to a failure,
  //  there are also uncaught exceptions which indicate something unexpected happened
  //I believe unexpected exceptions should terminate the program with a stack trace
  //Malformed jimple is an unexpected runtime error for which it is better to halt the execution of the entire prog

  /**
    * Execution exceptions such as throw, divide by zero, null pointer etc
    */
  private trait ExceptionalControlFlow extends StmtResult
  private sealed case class ExecutionExceptionDivideByZero(stmt: Stmt) extends ExceptionalControlFlow
  //TODO: Later we probably want to make these exceptions from the class hierarchy of interpreted program



  private def wrapExprEvaluationException(exprResult: Option[Int],
                                          stmt: Stmt,
                                          successCondition: Int => NormalControlFlow): StmtResult = exprResult match{
    case Some(v) => successCondition(v)
    case None => ExecutionExceptionDivideByZero(stmt)
  }
  /**
    *
    * @param env concrete environment for the interpreter
    * @param stmt stmt to interpret
    * @return StmtResult conveys what control flow action needs to be taken as well as the information needed
    */
  private def interpret_stmt(env: Env, stmt: Stmt): StmtResult = stmt match{
    case ReturnStmt(op)  => ReturnFromBody(evaluate_expr(op, env).map(a => CInteger(a)))
    case AssignStmt(Local(varname),rval) => wrapExprEvaluationException(
      evaluate_expr(rval,env), stmt, a => InterpretNext(updateEnv(env,varname,a)))
    case IfStmt(condition,_) => wrapExprEvaluationException(
      evaluate_expr(condition,env), stmt, a => if (a == 0) InterpretNext(env) else InterpretConditionalJump(env))
    case GotoStmt(_) => InterpretConditionalJump(env)
    case _ => {
      ???
    }
  }
}
