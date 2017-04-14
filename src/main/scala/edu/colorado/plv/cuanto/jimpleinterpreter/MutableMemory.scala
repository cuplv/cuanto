package edu.colorado.plv.cuanto.jimpleinterpreter

import soot.jimple._
import soot.shimple.ShimpleExpr
import soot.{Local, SootMethod, Value}

import scala.collection.immutable.{HashMap, Stack}
import scala.collection.JavaConverters._

/**
  * Created by lumber on 4/13/17.
  */
class MutableMemory {
  private var wholeStack = new Stack[HashMap[Value, Int]]()
  private var heap: HashMap[Value, Int] = HashMap()

  def stackDepth: Int = wholeStack.size

  def getCurrentStack: HashMap[Value, Int] = wholeStack.top

  def popCurrentStack(): HashMap[Value, Int] = {
    val ret = wholeStack.top
    wholeStack = wholeStack.pop
    ret
  }

  def createNewStack(): Unit = { wholeStack = wholeStack.push(HashMap[Value, Int]()) }

  @deprecated("")
  def setVal(value: Value, i: Int): Unit = {
    value match {
      case _value: Local =>
        val newStack = getCurrentStack + (value -> i)
        wholeStack = wholeStack.pop
        wholeStack = wholeStack.push(newStack)
      case _value: Constant =>
        val newStack = getCurrentStack + (value -> i)
        wholeStack = wholeStack.pop
        wholeStack= wholeStack.push(newStack)
      case _value: Expr => ???
      case _value: Ref =>
        _value match {
          case ref: ConcreteRef => ref match {
            case array: ArrayRef => ???
            case field: FieldRef => ???
          }
          case ref: IdentityRef => ref match {
            case param: ParameterRef => ???
            case _this: ThisRef => ???
            case exception: CaughtExceptionRef => ???
          }
        }
    }
  }

  @deprecated("")
  def getVal(value: Value): Int = {
    value match {
      case _value: Local => getCurrentStack.getOrElse(_value, -1) // Uninitialized value of any variable is -1 (Hopefully this will help debug)
      case _value: Constant => getConstantVal(_value)
      case _value: Expr => eval(_value)
      case _value: Ref =>
        _value match {
          case ref: ConcreteRef => ref match {
            case array: ArrayRef => ???
            case field: FieldRef => ??? // field.getField.getNumber
          }
          case ref: IdentityRef => ref match {
            case param: ParameterRef => ??? // param.getIndex
            case _this: ThisRef => ??? // _this.getType
            case exception: CaughtExceptionRef => ???
          }
        }
    }
  }

  @deprecated("")
  def eval(expr: Expr): Int = {
    expr match {
      // case _expr: AnyNewExpr => ???
      case _expr: BinopExpr =>
        _expr match {
          case __expr: AddExpr =>
            getVal(__expr.getOp1) + getVal(__expr.getOp2)
          case __expr: AndExpr => ???
          case __expr: CmpExpr => ???
          case __expr: CmpgExpr => ???
          case __expr: CmplExpr => ???
          case __expr: ConditionExpr =>
            val left = getVal(__expr.getOp1)
            val right = getVal(__expr.getOp2)
            __expr match {
              case ___expr: EqExpr => if (left == right) 1 else 0
              case ___expr: GeExpr => if (left >= right) 1 else 0
              case ___expr: GtExpr => if (left > right) 1 else 0
              case ___expr: LeExpr => if (left <= right) 1 else 0
              case ___expr: LtExpr => if (left < right) 1 else 0
              case ___expr: NeExpr => if (left != right) 1 else 0
            }
          case __expr: DivExpr =>
            getVal(__expr.getOp1) / getVal(__expr.getOp2)
          case __expr: MulExpr =>
            getVal(__expr.getOp1) * getVal(__expr.getOp2)
          case __expr: OrExpr => ???
          case __expr: RemExpr => ???
          case __expr: ShlExpr => ???
          case __expr: SubExpr =>
            getVal(__expr.getOp1) - getVal(__expr.getOp2)
          case __expr: UshrExpr => ???
          case __expr: XorExpr => ???
        }
      case _expr: CastExpr => ???
      case _expr: InstanceOfExpr => ???
      case _expr: InvokeExpr => invoke(_expr) match {
        case Some(x) => x
        case None => ???
      }
      case _expr: NewArrayExpr => ???
      case _expr: NewExpr => ???
      case _expr: NewMultiArrayExpr => ???
      case _expr: ShimpleExpr => ???
      case _expr: UnopExpr => ???
    }
  }

  def invoke(expr: InvokeExpr): Option[Int] = {
    val argList: List[Int] = expr.getArgs.asScala.map { arg => getVal(arg) }.toList

    createNewStack()
    val ret = concreteInterpret(expr.getMethod, this, argList)
    popCurrentStack()
    ret
  }
}
