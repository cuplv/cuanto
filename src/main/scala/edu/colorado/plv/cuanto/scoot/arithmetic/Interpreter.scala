package edu.colorado.plv.cuanto.scoot
package arithmetic

import soot._
import soot.dexpler.typing.UntypedConstant
import soot.jimple._
import soot.shimple.ShimpleExpr
import soot.shimple.toolkits.scalar.SEvaluator.MetaConstant

import scala.collection.immutable.HashMap

/** Implement an interpreter for soot objects that represent
  * arithemtic expressions
  */
object Interpreter {

  /** Interpreter for an arithmetic expression encoded as a series of
    * assignemnt statements.
    *
    * For now, there is a single variable ([[Builder.acc]]) being
    * updated, which is the value that should be returned */
  def denote(b: Body): Int = ???

  /** Interpreter component for evaluating an arithmetic expression
    * encoded as a single `Value`
    *
    * This has been deprecated because to make multi-operation
    * expressions, evaulation needs to be done over several assignment
    * statements (wrapped in a `Body`) */
  @deprecated
  def denote(v: Value, memory: HashMap[Local, Int]): Int = v match {
    // case e: IntConstant => e.value
    // case e: UnopExpr => uop(e)(denote(e.getOp))
    // case e: BinopExpr => bop(e)(denote(e.getOp1), denote(e.getOp2))
    case _value: Local => memory.getOrElse(_value, 0) // Uninitialized value of any variable is 0
    case _value: Constant => getConstantVal(_value)
    case _value: Expr => eval(_value, memory)
    case _value: Ref => ??? // Ignore
  }

  /** Interpret an arithmetic unary operator node, getting back a
    * function that performs the operation */
  def uop(op: UnopExpr): Int => Int = op match {
    case _: NegExpr => _ * -1
  }

  /** Interpret an arithemetic binary operator node, getting back a
    * function that performs the operation */
  def bop(op: BinopExpr): (Int, Int) => Int = op match {
    case _op: AddExpr => _ + _
    case _op: AndExpr => ???
    case _op: CmpExpr => ???
    case _op: CmpgExpr => ???
    case _op: CmplExpr => ???
    case _op: ConditionExpr => condop(_op) // TODO: if true: return 1, else: return 0
    case _op: DivExpr => _ / _
    case _op: MulExpr => _ * _
    case _op: OrExpr => ???
    case _op: RemExpr => ???
    case _op: ShlExpr => ???
    case _op: SubExpr => _ - _
    case _op: UshrExpr => ???
    case _op: XorExpr => ???
  }

  def condop(op: ConditionExpr): (Int, Int) => Boolean = op match {
    case _: EqExpr => _ == _
    case _: GeExpr => _ >= _
    case _: GtExpr => _ > _
    case _: LeExpr => _ <= _
    case _: LtExpr => _ < _
    case _: NeExpr => _ != _
  }

  def eval(expr: Expr, memory: HashMap[Local, Int]): Int = {
    expr match {
      case _expr: AnyNewExpr => 0
      case _expr: BinopExpr => bop(_expr)(denote(_expr.getOp1, memory), denote(_expr.getOp2, memory))
      case _expr: CastExpr => 0
      case _expr: InstanceOfExpr => 0
      case _expr: InvokeExpr => 0
      case _expr: NewArrayExpr => 0
      case _expr: NewExpr => 0
      case _expr: NewMultiArrayExpr => 0
      case _expr: ShimpleExpr => 0
      case _expr: UnopExpr => uop(_expr)(denote(_expr.getOp, memory))
    }
  }

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
              case __cons: LongConstant => __cons.value.toInt; ???
            }
          case _cons: RealConstant =>
            _cons match {
              case __cons: DoubleConstant => __cons.value.toInt; ???
              case __cons: FloatConstant => __cons.value.toInt; ???
            }
        }
      case cons: StringConstant => ???
      case cons: UntypedConstant => ???
    }
  }
}
