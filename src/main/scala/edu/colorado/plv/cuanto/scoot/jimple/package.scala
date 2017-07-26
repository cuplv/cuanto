package edu.colorado.plv.cuanto.scoot

import soot.jimple.JimpleValueSwitch
import scala.language.reflectiveCalls //IntelliJ warning on retValue orders this

/**
  * Created by Jared on 6/13/2017.
  */
package object jimple {
  implicit def convertAddExpr(dt: soot.jimple.AddExpr) : AddExpr = new AddExpr(dt)

  implicit def convertDivExpr(dt: soot.jimple.DivExpr) : DivExpr = new DivExpr(dt)

  implicit def convertIntConstant(dt: soot.jimple.IntConstant) : IntConstant = new IntConstant(dt)

  implicit def convertLocal(dt: soot.Local) : Local = new Local(dt)

  implicit def convertMulExpr(dt: soot.jimple.MulExpr) : MulExpr = new MulExpr(dt)

  implicit def convertNegExpr(dt: soot.jimple.NegExpr) : NegExpr = new NegExpr(dt)

  implicit def convertSubExpr(dt: soot.jimple.SubExpr) : SubExpr = new SubExpr(dt)

  implicit def convertValue(dt: soot.Value) : Value = {
    val s = new JimpleValueSwitch() {
      var retValue : Option[Value] = None

      //ExprSwitch methods
      override def caseAddExpr(addExpr: soot.jimple.AddExpr): Unit = retValue = Some(convertAddExpr(addExpr))

      override def caseAndExpr(andExpr: soot.jimple.AndExpr): Unit = ???

      override def caseCastExpr(castExpr: soot.jimple.CastExpr): Unit = ???

      override def caseCmpExpr(cmpExpr: soot.jimple.CmpExpr): Unit = ???

      override def caseCmpgExpr(cmpgExpr: soot.jimple.CmpgExpr): Unit = ???

      override def caseCmplExpr(cmplExpr: soot.jimple.CmplExpr): Unit = ???

      override def caseDivExpr(divExpr: soot.jimple.DivExpr): Unit = retValue = Some(convertDivExpr(divExpr))

      override def caseDynamicInvokeExpr(dynamicInvokeExpr: soot.jimple.DynamicInvokeExpr): Unit = ???

      override def caseEqExpr(eqExpr: soot.jimple.EqExpr): Unit = ???

      override def caseGeExpr(geExpr: soot.jimple.GeExpr): Unit = ???

      override def caseGtExpr(gtExpr: soot.jimple.GtExpr): Unit = ???

      override def caseInstanceOfExpr(instanceOfExpr: soot.jimple.InstanceOfExpr): Unit = ???

      override def caseInterfaceInvokeExpr(interfaceInvokeExpr: soot.jimple.InterfaceInvokeExpr): Unit = ???

      override def caseLeExpr(leExpr: soot.jimple.LeExpr): Unit = ???

      override def caseLengthExpr(lengthExpr: soot.jimple.LengthExpr): Unit = ???

      override def caseLtExpr(ltExpr: soot.jimple.LtExpr): Unit = ???

      override def caseMulExpr(mulExpr: soot.jimple.MulExpr): Unit = retValue = Some(convertMulExpr(mulExpr))

      override def caseNeExpr(neExpr: soot.jimple.NeExpr): Unit = ???

      override def caseNegExpr(negExpr: soot.jimple.NegExpr): Unit = retValue = Some(convertNegExpr(negExpr))

      override def caseNewArrayExpr(newArrayExpr: soot.jimple.NewArrayExpr): Unit = ???

      override def caseNewExpr(newExpr: soot.jimple.NewExpr): Unit = ???

      override def caseNewMultiArrayExpr(newMultiArrayExpr: soot.jimple.NewMultiArrayExpr): Unit = ???

      override def caseOrExpr(orExpr: soot.jimple.OrExpr): Unit = ???

      override def caseRemExpr(remExpr: soot.jimple.RemExpr): Unit = ???

      override def caseShlExpr(shlExpr: soot.jimple.ShlExpr): Unit = ???

      override def caseShrExpr(shrExpr: soot.jimple.ShrExpr): Unit = ???

      override def caseSpecialInvokeExpr(specialInvokeExpr: soot.jimple.SpecialInvokeExpr): Unit = ???

      override def caseStaticInvokeExpr(staticInvokeExpr: soot.jimple.StaticInvokeExpr): Unit = ???

      override def caseSubExpr(subExpr: soot.jimple.SubExpr): Unit = retValue = Some(convertSubExpr(subExpr))

      override def caseUshrExpr(ushrExpr: soot.jimple.UshrExpr): Unit = ???

      override def caseVirtualInvokeExpr(virtualInvokeExpr: soot.jimple.VirtualInvokeExpr): Unit = ???

      override def caseXorExpr(xorExpr: soot.jimple.XorExpr): Unit = ???


      //ConstantSwitch methods
      override def caseClassConstant(classConstant: soot.jimple.ClassConstant): Unit = ???

      override def caseDoubleConstant(doubleConstant: soot.jimple.DoubleConstant): Unit = ???

      override def caseFloatConstant(floatConstant: soot.jimple.FloatConstant): Unit = ???

      override def caseIntConstant(intConstant: soot.jimple.IntConstant): Unit =
        retValue = Some(convertIntConstant(intConstant))

      override def caseLongConstant(longConstant: soot.jimple.LongConstant): Unit = ???

      override def caseMethodHandle(methodHandle: soot.jimple.MethodHandle): Unit = ???

      override def caseNullConstant(nullConstant: soot.jimple.NullConstant): Unit = ???

      override def caseStringConstant(stringConstant: soot.jimple.StringConstant): Unit = ???


      //RefSwitch methods
      override def caseArrayRef(arrayRef: soot.jimple.ArrayRef): Unit = ???

      override def caseCaughtExceptionRef(caughtExceptionRef: soot.jimple.CaughtExceptionRef): Unit = ???

      override def caseInstanceFieldRef(instanceFieldRef: soot.jimple.InstanceFieldRef): Unit = ???

      override def caseParameterRef(parameterRef: soot.jimple.ParameterRef): Unit = ???

      override def caseStaticFieldRef(staticFieldRef: soot.jimple.StaticFieldRef): Unit = ???

      override def caseThisRef(thisRef: soot.jimple.ThisRef): Unit = ???


      //JimpleValueSwith methods
      override def caseLocal(local: soot.Local): Unit = retValue = Some(convertLocal(local))

      override def defaultCase(o: Object): Unit = throw new Exception("unrecognized conversion")
    }
    dt.apply(s)
    s.retValue.getOrElse{ throw new Exception("unreachable None in convertValue") }
  }
}
