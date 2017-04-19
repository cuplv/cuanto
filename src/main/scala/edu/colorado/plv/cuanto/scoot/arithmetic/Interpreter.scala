package edu.colorado.plv.cuanto.scoot
package arithmetic

//import soot._
//import soot.jimple._
import ir._

import scala.collection.immutable.HashMap

/** Implement an interpreter for sequences of Jimple assignment
  * statements that represent integer arithmetic programs
  */
object Interpreter {

  /** An "execution environment" or state, mapping variables (of type
    * `Local`) to integer values */
  type Env = Map[Local,Int]

  /** An environment with no assigned variables */
  val emptyEnv: Env = new HashMap[Local,Int]()

  private def some[A](a: A): Option[A] = Some(a)

  /** Step an environment forward over a single statement */
  def step(stmt: AssignStmt)(env: Env): Option[Env] = {
    val varNameO: Option[Local] = stmt.leftOp match {
      case l: Local => Some(l)
      case _ => None
    }
    val newValueO: Option[Int] = denote(stmt.rightOp, env)
    for {
      varName <- varNameO
      newValue <- newValueO
    } yield env + (varName -> newValue)
  }


  /** Interpret the integer value of a variable mutated over a sequence
    * of assignment statements */
  def denote(ss: Traversable[AssignStmt], v: Local): Option[Int] =
    denote(ss).flatMap(_ get v)

  def denote(ss: Traversable[AssignStmt]): Option[Env] =
    ss.foldLeft(some(emptyEnv))((env,stmt) => env.flatMap(step(stmt)))

  /** Interpret arithmetic expressions encoded as a single `Value` */
  def denote(v: Value, env: Env = emptyEnv): Option[Int] = v match {
    case Local(n) => env get v.asInstanceOf[Local]
    //case v: IntConstant => Some(v.value)
    case BinopExpr(e1, e2) => for {
      op <- bop(v.asInstanceOf[BinopExpr])
      arg1 <- denote(e1, env)
      arg2 <- denote(e2, env)
    } yield op(arg1, arg2)
    case UnopExpr(e) => for {
      op <- uop(v.asInstanceOf[UnopExpr])
      arg <- denote(e, env)
    } yield op(arg)
  }

  /** Interpret an arithmetic unary operator node, getting back a
    * function that performs the operation */
  def uop(op: UnopExpr): Option[Int => Int] = op match {
    case _: NegExpr => Some(_ * -1)
    case _ => None
  }

  /** Interpret an arithemetic binary operator node, getting back a
    * function that performs the operation */
  def bop(op: BinopExpr): Option[(Int, Int) => Int] = op match {
    case _: AddExpr => Some(_ + _)
    case _: SubExpr => Some(_ - _)
    case _: DivExpr => Some(_ / _)
    case _: MulExpr => Some(_ * _)
    case _ => None
  }

}
