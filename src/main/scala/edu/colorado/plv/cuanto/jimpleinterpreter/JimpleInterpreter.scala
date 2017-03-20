package edu.colorado.plv.cuanto.jimpleinterpreter

import java.util

import soot.JastAddJ.{AdditiveExpr, ArithmeticExpr, BitwiseExpr, LogicalExpr, ModExpr, MultiplicativeExpr, RelationalExpr, ShiftExpr}
import soot.dexpler.typing.UntypedConstant
import soot.jimple._
import soot.shimple.{Shimple, ShimpleExpr}
import soot.shimple.toolkits.scalar.SEvaluator.MetaConstant
import soot.{Local, Scene, SceneTransformer}

import scala.collection.JavaConverters._
import scala.collection.immutable.HashMap

/**
  * Created by Lumber on 3/19/2017.
  */
class JimpleInterpreter() extends SceneTransformer {
    var memory: HashMap[Local, Any] = new HashMap()

    override def internalTransform(s: String, map: util.Map[String, String]): Unit = {
        val mainClass = Scene.v().getMainClass
        val mainMethod = Scene.v().getMainMethod

        val methodBody = mainMethod.retrieveActiveBody()
        val stmtIt = methodBody.getUnits.iterator()
        methodBody.getLocals
        while (stmtIt.hasNext) {
            stmtIt.next()
        }
        println(methodBody)
    }

    def concreteInterpret(statement: Stmt): Unit = {
        statement match {
            case stmt: DefinitionStmt =>
                stmt match {
                    case _stmt: AssignStmt =>
                        _stmt.getLeftOp match {
                            case left: Local =>
                                _stmt.getRightOp match {
                                    case right: Constant =>
                                        memory = memory + (left -> getNumericalConstantVal(right))
                                    case right: Local =>
                                        // The uninitialized value of a variable is 0
                                        memory = memory + (left -> memory.getOrElse(right, 0))
                                    case right: Expr =>
                                }
                            case left@_ => println(left.getClass); System.exit(-1)
                        }
                    case _stmt: IdentityStmt => // Ignore
                }
            case stmt: GotoStmt =>
                println(stmt.getClass)
            case stmt: IfStmt =>
                println(stmt.getClass)
            case stmt: ReturnStmt =>
                println(stmt.getClass)
            case stmt: ReturnVoidStmt => // Ignore
            case stmt: InvokeStmt => // Ignore
            case stmt: SwitchStmt => // Ignore
            case stmt: MonitorStmt => // Ignore
            case stmt: ThrowStmt => // Ignore
            case stmt: BreakpointStmt => // Ignore
            case stmt: NopStmt => // Ignore
            case stmt: RetStmt => // Ignore
            case stmt@_ => System.err.print(stmt.getClass)
        }

    }

    def eval(expr: Expr): Any = {
        expr match {
            case _expr: AnyNewExpr =>
            case _expr: BinopExpr =>
                _expr match {
                    case __expr: AddExpr =>
                        __expr.getOp1
                    case __expr: AndExpr =>
                    case __expr: CmpExpr =>
                    case __expr: CmpgExpr =>
                    case __expr: CmplExpr =>
                    case __expr: DivExpr =>
                    case __expr: MulExpr =>
                    case __expr: OrExpr =>
                    case __expr: RemExpr =>
                    case __expr: ShlExpr =>
                    case __expr: SubExpr =>
                    case __expr: UshrExpr =>
                    case __expr: XorExpr =>
                }
            case _expr: CastExpr =>
            case _expr: InstanceOfExpr =>
            case _expr: InvokeExpr =>
            case _expr: NewArrayExpr =>
            case _expr: NewExpr =>
            case _expr: NewMultiArrayExpr =>
            case _expr: ShimpleExpr =>
            case _expr: UnopExpr =>
        }
    }

    // TODO: a better return type?
    def getNumericalConstantVal(constant: Constant): Any = {
        constant match {
            case cons: ClassConstant =>
            case cons: MetaConstant =>
            case cons: MethodHandle =>
            case cons: NullConstant =>
            case cons: NumericConstant =>
                cons match {
                    case _cons: ArithmeticConstant =>
                        _cons match {
                            case __cons: IntConstant => __cons.value
                            case __cons: LongConstant => __cons.value
                        }
                    case _cons: RealConstant =>
                        _cons match {
                            case __cons: DoubleConstant => __cons.value
                            case __cons: FloatConstant => __cons.value
                        }
                }
            case cons: StringConstant =>
            case cons: UntypedConstant =>
        }
    }
}
