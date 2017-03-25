package edu.colorado.plv.cuanto.scoot
package arithmetic

import soot._
import soot.jimple._

import scala.collection.immutable.HashMap

/** Implement an interpreter for soot objects that represent
  * integer arithemtic expressions
  */
object Interpreter {

  type Env = Map[Local,Int]

  val newEnv: Env = new HashMap[Local,Int]()

  def some[A](a: A): Option[A] = Some(a)

  /** Step an environment forward over a single statment */
  def step(stmt: Stmt)(env: Env): Option[Env] = ???

  /** Interpreter for the value of a variable mutated over a sequence of
    * statements */
  def denote(ss: Traversable[Stmt], v: Local): Option[Int] =
    denote(ss).flatMap(_ get v)

  def denote(ss: Traversable[Stmt]): Option[Env] =
    ss.foldLeft(some(newEnv))((env,stmt) => env.flatMap(step(stmt)))

  /** Interpreter component for evaluating an arithmetic expression
    * encoded as a single `Value` */
  def denote(env: Env, v: Value): Option[Int] = v match {
    case v: Local => env get v
    case v: IntConstant => Some(v.value)
    case v: BinopExpr => for {
      op <- bop(v)
      arg1 <- denote(env, v.getOp1())
      arg2 <- denote(env, v.getOp2())
    } yield op(arg1, arg2)
    case v: UnopExpr => for {
      op <- uop(v)
      arg <- denote(env, v.getOp())
    } yield op(arg)
  }

  def denote(v: Value): Option[Int] = denote(newEnv, v)

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
