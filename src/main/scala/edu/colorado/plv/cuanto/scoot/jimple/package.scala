package edu.colorado.plv.cuanto.scoot
import soot.jimple
import soot.jimple.JimpleValueSwitch

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
      override def caseAddExpr(addExpr: jimple.AddExpr): Unit = retValue = Some(convertAddExpr(addExpr))

      override def caseAndExpr(andExpr: jimple.AndExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseCastExpr(castExpr: jimple.CastExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseCmpExpr(cmpExpr: jimple.CmpExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseCmpgExpr(cmpgExpr: jimple.CmpgExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseCmplExpr(cmplExpr: jimple.CmplExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseDivExpr(divExpr: jimple.DivExpr): Unit = retValue = Some(convertDivExpr(divExpr))

      override def caseDynamicInvokeExpr(dynamicInvokeExpr: jimple.DynamicInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseEqExpr(eqExpr: jimple.EqExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseGeExpr(geExpr: jimple.GeExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseGtExpr(gtExpr: jimple.GtExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseInstanceOfExpr(instanceOfExpr: jimple.InstanceOfExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseInterfaceInvokeExpr(interfaceInvokeExpr: jimple.InterfaceInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseLeExpr(leExpr: jimple.LeExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseLengthExpr(lengthExpr: jimple.LengthExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseLtExpr(ltExpr: jimple.LtExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseMulExpr(mulExpr: jimple.MulExpr): Unit = retValue = Some(convertMulExpr(mulExpr))

      override def caseNeExpr(neExpr: jimple.NeExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseNegExpr(negExpr: jimple.NegExpr): Unit = retValue = Some(convertNegExpr(negExpr))

      override def caseNewArrayExpr(newArrayExpr: jimple.NewArrayExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseNewExpr(newExpr: jimple.NewExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseNewMultiArrayExpr(newMultiArrayExpr: jimple.NewMultiArrayExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseOrExpr(orExpr: jimple.OrExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseRemExpr(remExpr: jimple.RemExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseShlExpr(shlExpr: jimple.ShlExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseShrExpr(shrExpr: jimple.ShrExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseSpecialInvokeExpr(specialInvokeExpr: jimple.SpecialInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseStaticInvokeExpr(staticInvokeExpr: jimple.StaticInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseSubExpr(subExpr: jimple.SubExpr): Unit = retValue = Some(convertSubExpr(subExpr))

      override def caseUshrExpr(ushrExpr: jimple.UshrExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseVirtualInvokeExpr(virtualInvokeExpr: jimple.VirtualInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseXorExpr(xorExpr: jimple.XorExpr): Unit = throw new Exception("unsupported Expr conversion")


      //ConstantSwitch methods
      override def caseClassConstant(classConstant: jimple.ClassConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseDoubleConstant(doubleConstant: jimple.DoubleConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseFloatConstant(floatConstant: jimple.FloatConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseIntConstant(intConstant: jimple.IntConstant): Unit =
        retValue = Some(convertIntConstant(intConstant))

      override def caseLongConstant(longConstant: jimple.LongConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseMethodHandle(methodHandle: jimple.MethodHandle): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseNullConstant(nullConstant: jimple.NullConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseStringConstant(stringConstant: jimple.StringConstant): Unit =
        throw new Exception("unsupported Constant conversion")


      //RefSwitch methods
      override def caseArrayRef(arrayRef: jimple.ArrayRef): Unit = throw new Exception("unsupported Ref conversion")

      override def caseCaughtExceptionRef(caughtExceptionRef: jimple.CaughtExceptionRef): Unit =
        throw new Exception("unsupported Ref conversion")

      override def caseInstanceFieldRef(instanceFieldRef: jimple.InstanceFieldRef): Unit =
        throw new Exception("unsupported Ref conversion")

      override def caseParameterRef(parameterRef: jimple.ParameterRef): Unit =
        throw new Exception("unsupported Ref conversion")

      override def caseStaticFieldRef(staticFieldRef: jimple.StaticFieldRef): Unit =
        throw new Exception("unsupported Ref conversion")

      override def caseThisRef(thisRef: jimple.ThisRef): Unit = throw new Exception("unsupported Ref conversion")


      //JimpleValueSwith methods
      override def caseLocal(local: soot.Local): Unit = retValue = Some(convertLocal(local))

      override def defaultCase(o: Object): Unit = throw new Exception("unrecognized conversion")
    }
    dt.apply(s)
    s.retValue.getOrElse{ throw new Exception("unreachable None in convertValue") }
  }
}
