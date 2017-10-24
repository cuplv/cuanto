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

  case class StackFrame(returnLocation: Option[(Body, Stmt)], locals : Map[String, CValue], returnValueLocation : Option[Local])

  /** An "execution environment" or state, mapping variables (of type
    * `Local`) to integer values */
  //type Env = Map[String,CValue]

  private val emptyLocals = new HashMap[String, CValue]()
  /** An environment with no assigned variables */
  private val emptyEnv: StackFrame = StackFrame(None, emptyLocals, None) //TODO: update environment
  def emptyEnv(body : Body, stmt : Stmt, returnValueLocation : Option[Local]) =
    StackFrame(Some((body,stmt)), emptyLocals, returnValueLocation)

  /** Interpret arithmetic expressions encoded as a single `Value` */
  def evaluate_expr(v: Value, env: StackFrame): Option[CValue] = v match { //TODO: update denote
    case Local(s) => {
      Some(getEnv(env,s))
    }
    case IntConstant(v) => {
      Some(CInteger(v))
    }
    case AddExpr(e1, e2) => for {
      arg1 <- evaluate_expr(e1, env)
      arg2 <- evaluate_expr(e2, env)
    } yield ConcreteMemory.add(arg1, arg2)
    case SubExpr(e1, e2) => for {
      arg1 <- evaluate_expr(e1, env)
      arg2 <- evaluate_expr(e2, env)
    } yield ConcreteMemory.sub(arg1,arg2)
    case MulExpr(e1, e2) => for {
      arg1 <- evaluate_expr(e1, env)
      arg2 <- evaluate_expr(e2, env)
    } yield ConcreteMemory.mul(arg1,arg2)
    case DivExpr(e1, e2) => for {
      arg1 <- evaluate_expr(e1, env)
      arg2 <- {val res = evaluate_expr(e2, env); if(res != 0) res else None}
    } yield ConcreteMemory.div(arg1,arg2)
    case NegExpr(e) => for {
      arg <- evaluate_expr(e, env)
    } yield ConcreteMemory.neg(arg)
    case EqExpr(e1,e2) => for{
      arg1 <- evaluate_expr(e1,env)
      arg2 <- evaluate_expr(e2,env)
    } yield  ConcreteMemory.equ(arg1,arg2)
    case NeExpr(e1,e2) => for{
        arg1 <- evaluate_expr(e1,env)
        arg2 <- evaluate_expr(e2,env)
    }yield ConcreteMemory.nequ(arg1,arg2)
    case GeExpr(e1,e2) => for{
      arg1 <- evaluate_expr(e1,env)
      arg2 <- evaluate_expr(e2,env)
    }yield ConcreteMemory.ge(arg1,arg2)
    case _ => {
      ???
    }
  }
  def interpretBody(b : Body): Try[CValue] = {
    Try(internal_interpretBody(List(emptyEnv), b.getFirstNonIdentityStmt, b).getOrElse(throw new RuntimeException("interpreter exception")))
  }
  //TODO: update environment and update stack
  private def updateEnv(env: StackFrame, varname: String, value: CValue): StackFrame = env match{
    case StackFrame(loc, env, rvl) => StackFrame(loc, env + (varname -> value), rvl)
  }
  private def getEnv(env: StackFrame, varname : String) : CValue = env match {
    case StackFrame(_, env, _) => env.getOrElse (varname, throw new RuntimeException (s"Variable $varname not found, malformed jimple") )
  }
  private def malformedJimple(): Nothing = throw new RuntimeException("malformed jimple")
  @tailrec
  private def internal_interpretBody(stack : List[StackFrame], loc: Stmt, b : Body): Option[CValue] = {
    //normal successor, conditional successor TODO: exceptional successor
    val successor: (Option[Stmt], Option[Stmt]) = b.getSuccessors(loc)
    interpret_stmt(stack.head, loc) match {
      case InterpretNext(env) => internal_interpretBody(env :: stack.tail, successor._1.getOrElse(malformedJimple()), b)
      case InterpretConditionalJump(env) =>
        internal_interpretBody(env :: stack.tail,
          successor._2.getOrElse(malformedJimple()),b)
      case ReturnFromBody(returnValue) => stack match{
        case h :: Nil => returnValue
        case StackFrame(Some((body,stmt)), _, Some(Local(varname))) :: (us@StackFrame(r,e,l)) :: tail => {
          val newEnv = returnValue.map(updateEnv(us,varname,_)).getOrElse(us)
          val newFrame = StackFrame(r,newEnv.locals,l)
          internal_interpretBody(newFrame :: tail, stmt, body)
        }
        case _ =>
          throw new RuntimeException("malformed stack exception")
      }
      case Invoke(method, args, returnValueLocation) =>
        val body = new Body(method.getActiveBody)
        val newEnviornment = emptyEnv(b, successor._1.getOrElse(throw new RuntimeException("malformed jimple")), returnValueLocation)
//        val newEnvironment = emptyEnv(b,successor._1.)
        internal_interpretBody( newEnviornment ::
          stack, body.getFirstNonIdentityStmt(), body)
      case ExecutionExceptionDivideByZero(stmt) => ???
    }
  }

  /**
    * Data interpreter needs to understand how to continue interpretation after processing command
    */
  private trait StmtResult
  private trait NormalControlFlow extends StmtResult
  private sealed case class InterpretNext(newEnvironment : StackFrame) extends NormalControlFlow
  private sealed case class InterpretConditionalJump(newEnvironment: StackFrame) extends NormalControlFlow
  private sealed case class ReturnFromBody(result: Option[CValue])  extends NormalControlFlow
  private sealed case class Invoke(method: soot.SootMethod, arguments : List[Value], returnValueLocation : Option[Local]) extends NormalControlFlow
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



  private def wrapExprEvaluationException(exprResult: Option[CValue],
                                          stmt: Stmt,
                                          successCondition: CValue => NormalControlFlow): StmtResult = exprResult match{
    case Some(v) => successCondition(v)
    case None => ExecutionExceptionDivideByZero(stmt)
  }
  /**
    *
    * @param env concrete environment for the interpreter
    * @param stmt stmt to interpret
    * @return StmtResult conveys what control flow action needs to be taken as well as the information needed
    */
  private def interpret_stmt(env: StackFrame, stmt: Stmt): StmtResult = stmt match{
    case ReturnStmt(op)  => ReturnFromBody(evaluate_expr(op, env).map(a => a))
    case AssignStmt(l@Local(varname), StaticInvokeExpr(method, args)) => Invoke(method, args, Some(l))
    case AssignStmt(Local(varname),rval) => wrapExprEvaluationException(
      evaluate_expr(rval,env), stmt, a => InterpretNext(updateEnv(env,varname,a)))
    case IfStmt(condition,_) => wrapExprEvaluationException(
      evaluate_expr(condition,env), stmt, a => if (ConcreteMemory.isZero(a)) InterpretNext(env) else InterpretConditionalJump(env))
    case GotoStmt(_) => InterpretConditionalJump(env)
    case _ => {
      ???
    }
  }
}
