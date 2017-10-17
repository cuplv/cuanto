package edu.colorado.plv.cuanto.scoot.concrete_interpreter

import com.sun.org.glassfish.external.amx.MBeanListener.CallbackImpl
import edu.colorado.plv.cuanto.scoot.concrete_interpreter

/**
  * @author Shawn Meier
  *         Created on 9/29/17.
  */
object ConcreteMemory {
  def div(v1 : CValue, v2: CValue) = (v1,v2) match{
    case (CInteger(i1), CInteger(i2)) => CInteger(i1/i2)
    case _ => ???
  }
  def mul(v1: CValue, v2: CValue) = (v1,v2) match{
    case (CInteger(i1), CInteger(i2)) => CInteger(i1*i2)
    case _ => ???
  }

  def add(v1 : CValue, v2 : CValue) = (v1,v2) match{
    case (CInteger(i1), CInteger(i2)) => CInteger(i1+i2)
    case _ => ???
  }
  def sub(v1 :CValue, v2 :CValue) = (v1,v2) match{
    case (CInteger(i1), CInteger(i2)) => CInteger(i1 - i2)
  }
  def neg(v1 : CValue) = v1 match{
    case CInteger(i1) => CInteger(-i1)
    case _ => ???
  }
  def equ(v1 :CValue, v2 :CValue) = (v1,v2) match{
    case (CInteger(i1),CInteger(i2)) => booleanToInteger(i1 == i2)
    case _ => ???
  }
  def nequ(v1 :CValue, v2: CValue) = (v1,v2) match{
    case (CInteger(i1), CInteger(i2)) => booleanToInteger(i1 != i2)
    case _ => ???
  }
  def ge(v1 :CValue, v2: CValue) = (v1,v2) match{
    case (CInteger(i1), CInteger(i2)) => booleanToInteger(i1 >= i2)
    case _ => ???
  }
  def booleanToInteger(b : Boolean): CInteger = if(b) CInteger(1) else CInteger(0)
  def isZero(a: CValue): Boolean = a match{
    case CInteger(0) => true
    case CInteger(i) => false
    case _ => ???
  }
}

sealed trait CValue
sealed trait CPrimitive extends CValue
case class CFloat(v: Float) extends CPrimitive
case class CBool(v: Boolean) extends CPrimitive
case class CInteger(v: Int) extends CPrimitive

trait CReference extends CValue
case class CNull()
case class CObject(objectType: String, fields: Map[String,CValue]) extends CReference

