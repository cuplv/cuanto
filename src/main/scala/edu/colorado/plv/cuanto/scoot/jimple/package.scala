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
      override def caseAddExpr(addExpr: soot.jimple.AddExpr): Unit = retValue = Some(convertAddExpr(addExpr))

      override def caseAndExpr(andExpr: soot.jimple.AndExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseCastExpr(castExpr: soot.jimple.CastExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseCmpExpr(cmpExpr: soot.jimple.CmpExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseCmpgExpr(cmpgExpr: soot.jimple.CmpgExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseCmplExpr(cmplExpr: soot.jimple.CmplExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseDivExpr(divExpr: soot.jimple.DivExpr): Unit = retValue = Some(convertDivExpr(divExpr))

      override def caseDynamicInvokeExpr(dynamicInvokeExpr: soot.jimple.DynamicInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseEqExpr(eqExpr: soot.jimple.EqExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseGeExpr(geExpr: soot.jimple.GeExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseGtExpr(gtExpr: soot.jimple.GtExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseInstanceOfExpr(instanceOfExpr: soot.jimple.InstanceOfExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseInterfaceInvokeExpr(interfaceInvokeExpr: soot.jimple.InterfaceInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseLeExpr(leExpr: soot.jimple.LeExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseLengthExpr(lengthExpr: soot.jimple.LengthExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseLtExpr(ltExpr: soot.jimple.LtExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseMulExpr(mulExpr: soot.jimple.MulExpr): Unit = retValue = Some(convertMulExpr(mulExpr))

      override def caseNeExpr(neExpr: soot.jimple.NeExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseNegExpr(negExpr: soot.jimple.NegExpr): Unit = retValue = Some(convertNegExpr(negExpr))

      override def caseNewArrayExpr(newArrayExpr: soot.jimple.NewArrayExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseNewExpr(newExpr: soot.jimple.NewExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseNewMultiArrayExpr(newMultiArrayExpr: soot.jimple.NewMultiArrayExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseOrExpr(orExpr: soot.jimple.OrExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseRemExpr(remExpr: soot.jimple.RemExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseShlExpr(shlExpr: soot.jimple.ShlExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseShrExpr(shrExpr: soot.jimple.ShrExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseSpecialInvokeExpr(specialInvokeExpr: soot.jimple.SpecialInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseStaticInvokeExpr(staticInvokeExpr: soot.jimple.StaticInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseSubExpr(subExpr: soot.jimple.SubExpr): Unit = retValue = Some(convertSubExpr(subExpr))

      override def caseUshrExpr(ushrExpr: soot.jimple.UshrExpr): Unit = throw new Exception("unsupported Expr conversion")

      override def caseVirtualInvokeExpr(virtualInvokeExpr: soot.jimple.VirtualInvokeExpr): Unit =
        throw new Exception("unsupported Expr conversion")

      override def caseXorExpr(xorExpr: soot.jimple.XorExpr): Unit = throw new Exception("unsupported Expr conversion")


      //ConstantSwitch methods
      override def caseClassConstant(classConstant: soot.jimple.ClassConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseDoubleConstant(doubleConstant: soot.jimple.DoubleConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseFloatConstant(floatConstant: soot.jimple.FloatConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseIntConstant(intConstant: soot.jimple.IntConstant): Unit =
        retValue = Some(convertIntConstant(intConstant))

      override def caseLongConstant(longConstant: soot.jimple.LongConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseMethodHandle(methodHandle: soot.jimple.MethodHandle): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseNullConstant(nullConstant: soot.jimple.NullConstant): Unit =
        throw new Exception("unsupported Constant conversion")

      override def caseStringConstant(stringConstant: soot.jimple.StringConstant): Unit =
        throw new Exception("unsupported Constant conversion")


      //RefSwitch methods
      override def caseArrayRef(arrayRef: soot.jimple.ArrayRef): Unit = throw new Exception("unsupported Ref conversion")

      override def caseCaughtExceptionRef(caughtExceptionRef: soot.jimple.CaughtExceptionRef): Unit =
        throw new Exception("unsupported Ref conversion")

      override def caseInstanceFieldRef(instanceFieldRef: soot.jimple.InstanceFieldRef): Unit =
        throw new Exception("unsupported Ref conversion")

      override def caseParameterRef(parameterRef: soot.jimple.ParameterRef): Unit =
        throw new Exception("unsupported Ref conversion")

      override def caseStaticFieldRef(staticFieldRef: soot.jimple.StaticFieldRef): Unit =
        throw new Exception("unsupported Ref conversion")

      override def caseThisRef(thisRef: soot.jimple.ThisRef): Unit = throw new Exception("unsupported Ref conversion")


      //JimpleValueSwith methods
      override def caseLocal(local: soot.Local): Unit = retValue = Some(convertLocal(local))

      override def defaultCase(o: Object): Unit = throw new Exception("unrecognized conversion")
    }
    dt.apply(s)
    s.retValue.getOrElse{ throw new Exception("unreachable None in convertValue") }
  }
}
