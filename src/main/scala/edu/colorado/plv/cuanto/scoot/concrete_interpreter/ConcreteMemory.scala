package edu.colorado.plv.cuanto.scoot.concrete_interpreter

/**
  * @author Shawn Meier
  *         Created on 9/29/17.
  */
object ConcreteMemory {}

sealed trait CValue
sealed trait CPrimitive extends CValue
case class CFloat(v: Float) extends CPrimitive
case class CBool(v: Boolean) extends CPrimitive
case class CInteger(v: Int) extends CPrimitive

trait CReference extends CValue
case class CNull()
case class CObject(objectType: String, fields: Map[String,CValue]) extends CReference

