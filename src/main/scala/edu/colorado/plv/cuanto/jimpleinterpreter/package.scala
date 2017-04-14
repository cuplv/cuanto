package edu.colorado.plv.cuanto

import soot.{Body, Local, SootMethod}
import soot.dexpler.typing.UntypedConstant
import soot.jimple._
import soot.shimple.toolkits.scalar.SEvaluator.MetaConstant

/**
  * Created by lumber on 4/13/17.
  */
package object jimpleinterpreter {
  @deprecated("")
  def getConstantVal(constant: Constant): Int = {
    constant match {
      case cons: ClassConstant => ???
      case cons: MetaConstant => ???
      case cons: MethodHandle => ???
      case cons: NullConstant => ???
      case cons: NumericConstant =>
        cons match {
          case _cons: ArithmeticConstant =>
            _cons match {
              case __cons: IntConstant => __cons.value
              case __cons: LongConstant => __cons.value.toInt
            }
          case _cons: RealConstant =>
            _cons match {
              case __cons: DoubleConstant => __cons.value.toInt
              case __cons: FloatConstant => __cons.value.toInt
            }
        }
      case cons: StringConstant => ???
      case cons: UntypedConstant => ???
    }
  }

  // TODO: is there a better way to do this?
  @deprecated("")
  private def getNextStmt(stmt: Stmt, body: Body): Stmt = {
    val it = body.getUnits.iterator()
    while (it.hasNext) {
      val nextStmt = it.next()
      if (nextStmt == stmt) {
        if (it.hasNext) {
          it.next() match {
            case s: Stmt => return s
            case _ => assert(false, "Type error: Next Stmt")
          }
        } else {
          return null
        }
      }
    }
    null
  }

  @deprecated("")
  def concreteInterpret(method: SootMethod, memory: MutableMemory, argList: List[Int]): Option[Int] = {
    var ret: Option[Int] = None
    val body = method.retrieveActiveBody()
    var pc = body.getUnits.getFirst match {
      case s: Stmt => s
      case _ => assert(false, "Type error: First statement of the method body"); null
    }
    while (pc != null) {
      val (newPC, retVal) = _concreteInterpret(pc, body, memory, argList)
      pc = newPC
      ret = retVal
    }
    ret
  }

  @deprecated("")
  private def _concreteInterpret(statement: Stmt, body: Body, memory: MutableMemory, argList: List[Int]): (Stmt, Option[Int]) = {
    val DEBUG = true
    if (DEBUG) println("[" + body.getMethod.getName + "] " + statement)

    var ret: Option[Int] = None

    val pc = statement match {
      case stmt: DefinitionStmt =>
        stmt match {
          case _stmt: AssignStmt =>
            _stmt.getLeftOp match {
              case left: Local =>
                memory.setVal(left, memory.getVal(_stmt.getRightOp))
              case left@_ => println(left.getClass); assert(false, "Left op of AssignStmt is not Local")
            }
          case _stmt: IdentityStmt =>
            // Initialize argument(s)
            _stmt.getLeftOp match {
              case left: Local => memory.setVal(left, argList(left.getName.substring(1).toInt))
              case left@_ => println(left.getClass); assert(false, "Left op of AssignStmt is not Local")
            }
        }
        getNextStmt(stmt, body)
      case stmt: GotoStmt =>
        stmt.getTarget match {
          case s: Stmt => s
          case _ => assert(false, "Type error: Target of GotoStmt"); null
        }
      case stmt: IfStmt =>
        val cond = memory.getVal(stmt.getCondition)
        if (cond == 1) {
          stmt.getTarget
        } else if (cond == 0) {
          getNextStmt(stmt, body)
        } else {
          assert(false, "Evaluation error in if condition"); null
        }
      case stmt: ReturnStmt => ret = Some(memory.getVal(stmt.getOp)); null
      case stmt: ReturnVoidStmt => null
      case stmt: InvokeStmt => // Ignore
        memory.invoke(stmt.getInvokeExpr)
        getNextStmt(stmt, body)
      case stmt: SwitchStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: MonitorStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: ThrowStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: BreakpointStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: NopStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: RetStmt => // Ignore
        getNextStmt(stmt, body)
    }
    (pc, ret)
  }
}
