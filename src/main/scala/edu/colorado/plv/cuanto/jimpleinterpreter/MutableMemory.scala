package edu.colorado.plv.cuanto.jimpleinterpreter

import soot.jimple._
import soot.{Local, Value}

import scala.collection.immutable.{HashMap, Stack}

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

  def createNewStack(): Unit = {
    wholeStack = wholeStack.push(HashMap[Value, Int]())
  }

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
        wholeStack = wholeStack.push(newStack)
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
      case _value: Expr => eval(_value, this)
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
}
