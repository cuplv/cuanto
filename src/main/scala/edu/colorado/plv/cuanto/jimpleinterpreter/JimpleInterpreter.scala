package edu.colorado.plv.cuanto.jimpleinterpreter

import java.util

import soot.dexpler.typing.UntypedConstant
import soot.jimple._
import soot.shimple.ShimpleExpr
import soot.shimple.toolkits.scalar.SEvaluator.MetaConstant
import soot.{Body, Local, Scene, SceneTransformer, Value}

import scala.collection.immutable.HashMap
import scala.collection.mutable

/**
  * Created by Lumber on 3/19/2017.
  */
class JimpleInterpreter(memory: mutable.HashMap[Local, Int]) extends SceneTransformer {
    val DEBUG = true
    // var memory: HashMap[Local, Int] = new HashMap()
    var pc: Stmt = _

    override def internalTransform(s: String, map: util.Map[String, String]): Unit = {
        // val mainClass = Scene.v().getMainClass
        val mainMethod = Scene.v().getMainMethod

        val methodBody = mainMethod.retrieveActiveBody()
        methodBody.getUnits.getFirst match {
            case s: Stmt => pc = s
            case _ => assert(false, "Type error: First statement of the method body")
        }
        while(pc != null) {
            concreteInterpret(pc, methodBody)
        }

        if (DEBUG) {
            println(methodBody)
            memory.foreach {
                case (variable, value) => println(variable + ": " + value)
            }
        }
    }

    // TODO: is there a better way to do this?
    @deprecated("")
    def getNextStmt(stmt: Stmt, body: Body): Stmt = {
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
                    // return null.asInstanceOf[Stmt]
                    return null
                }
            }
        }
        // null.asInstanceOf[Stmt]
        null
    }

    @deprecated("")
    def concreteInterpret(statement: Stmt, body: Body): Unit = {
        statement match {
            case stmt: DefinitionStmt =>
                stmt match {
                    case _stmt: AssignStmt =>
                        _stmt.getLeftOp match {
                            case left: Local =>
                                    // memory = memory + (left -> getVal(_stmt.getRightOp))
                                    memory.update(left, getVal(_stmt.getRightOp))
                            case left@_ => println(left.getClass); assert(false, "Left op of AssignStmt is not Local")
                        }
                    case _stmt: IdentityStmt => // Ignore
                }
                pc = getNextStmt(stmt, body)
            case stmt: GotoStmt =>
                stmt.getTarget match {
                    case s: Stmt => pc = s
                    case _ => assert(false, "Type error: Target of GotoStmt")
                }
            case stmt: IfStmt =>
                val cond = getVal(stmt.getCondition)
                if (cond == 1) {
                    pc = stmt.getTarget
                } else if (cond == 0) {
                    pc = getNextStmt(stmt, body)
                } else {
                    assert(false, "Evaluation error in if condition")
                }
            case stmt: ReturnStmt =>
                pc = null
            case stmt: ReturnVoidStmt => // Ignore
                pc = null
                pc = getNextStmt(stmt, body)
            case stmt: InvokeStmt => // Ignore
                pc = getNextStmt(stmt, body)
            case stmt: SwitchStmt => // Ignore
                pc = getNextStmt(stmt, body)
            case stmt: MonitorStmt => // Ignore
                pc = getNextStmt(stmt, body)
            case stmt: ThrowStmt => // Ignore
                pc = getNextStmt(stmt, body)
            case stmt: BreakpointStmt => // Ignore
                pc = getNextStmt(stmt, body)
            case stmt: NopStmt => // Ignore
                pc = getNextStmt(stmt, body)
            case stmt: RetStmt => // Ignore
                pc = getNextStmt(stmt, body)
        }

    }

    @deprecated("")
    def getVal(value: Value): Int = {
        value match {
            case _value: Local => memory.getOrElse(_value, 0) // Uninitialized value of any variable is 0
            case _value: Constant => getConstantVal(_value)
            case _value: Expr => eval(_value)
            case _value: Ref => 0 // Ignore
        }
    }

    @deprecated("")
    def eval(expr: Expr): Int = {
        expr match {
            case _expr: AnyNewExpr => 0
            case _expr: BinopExpr =>
                _expr match {
                    case __expr: AddExpr =>
                        getVal(__expr.getOp1) + getVal(__expr.getOp2)
                    case __expr: AndExpr => 0
                    case __expr: CmpExpr => 0
                    case __expr: CmpgExpr => 0
                    case __expr: CmplExpr => 0
                    case __expr: ConditionExpr =>
                        val left = getVal(__expr.getOp1)
                        val right = getVal(__expr.getOp2)
                        __expr match {
                            case ___expr: EqExpr => if (left == right) 1 else 0
                            case ___expr: GeExpr => if (left >= right) 1 else 0
                            case ___expr: GtExpr => if (left > right)  1 else 0
                            case ___expr: LeExpr => if (left <= right) 1 else 0
                            case ___expr: LtExpr => if (left < right)  1 else 0
                            case ___expr: NeExpr => if (left != right) 1 else 0
                        }
                    case __expr: DivExpr =>
                        getVal(__expr.getOp1) / getVal(__expr.getOp2)
                    case __expr: MulExpr =>
                        getVal(__expr.getOp1) * getVal(__expr.getOp2)
                    case __expr: OrExpr => 0
                    case __expr: RemExpr => 0
                    case __expr: ShlExpr => 0
                    case __expr: SubExpr =>
                        getVal(__expr.getOp1) - getVal(__expr.getOp2)
                    case __expr: UshrExpr => 0
                    case __expr: XorExpr => 0
                }
            case _expr: CastExpr => 0
            case _expr: InstanceOfExpr => 0
            case _expr: InvokeExpr => 0
            case _expr: NewArrayExpr => 0
            case _expr: NewExpr => 0
            case _expr: NewMultiArrayExpr => 0
            case _expr: ShimpleExpr => 0
            case _expr: UnopExpr => 0
        }
    }

    @deprecated("")
    def getConstantVal(constant: Constant): Int = {
        constant match {
            case cons: ClassConstant => 0
            case cons: MetaConstant => 0
            case cons: MethodHandle => 0
            case cons: NullConstant => 0
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
            case cons: StringConstant => 0
            case cons: UntypedConstant => 0
        }
    }
}
