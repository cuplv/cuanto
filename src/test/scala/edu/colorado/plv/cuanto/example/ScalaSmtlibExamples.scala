package edu.colorado.plv.cuanto
package example

import org.scalatest.tagobjects.Slow

import smtlib.{
  Interpreter
}
import smtlib.interpreters.{
  Z3Interpreter
}
import smtlib.parser.Terms.{
  SSymbol,
  Term,
  QualifiedIdentifier,
  SimpleIdentifier
}
import smtlib.parser.Commands.{
  Command,
  DeclareConst,
  Assert,
  CheckSat,
  GetModel,
  DefineFun,
  FunDef,
  Push,
  Pop
}
import smtlib.parser.CommandsResponses.{
  Success,
  CheckSatStatus,
  SatStatus,
  UnsatStatus,
  GetModelResponseSuccess,
  Error
}
import smtlib.theories.Ints.{
  IntSort,
  NumeralLit,
  GreaterEquals,
  GreaterThan,
  LessThan,
  Neg
}
import smtlib.theories.Constructors.{
  and,
  not => notTerm
}

/**
  * Usage examples based on the scala-smtlib integration tests
  * 
  * [[https://github.com/regb/scala-smtlib/blob/v0.2.1/src/it/scala/smtlib/it/SmtLibRunnerTests.scala]]
  * 
  * @author Nicholas Lewchenko
  */
class ScalaSmtlibExample extends CuantoSpec {

  "Evaluating SMT scripts" should "go like this" taggedAs(Slow) in {
    forAll (testCmds) { (cmd,res) => z3.eval(cmd) should equal (res) }
  }

  lazy val z3: Interpreter = Z3Interpreter.buildDefault

  val zero: Term = NumeralLit(0)
  val one: Term = NumeralLit(1)
  val three: Term = NumeralLit(3)

  val mkVar: SSymbol => Term = name =>
  QualifiedIdentifier(SimpleIdentifier(name))

  val xs: SSymbol = SSymbol("x")
  val ys: SSymbol = SSymbol("y")
  val x: Term = mkVar(xs)
  val y: Term = mkVar(ys)

  val goodExpr: Term =
    and(
      GreaterEquals(x,zero),
      notTerm(GreaterThan(x,three)),
      LessThan(y,x)
    )

  /** Models are returned as sequences of "define-fun" commands */
  val goodModel: List[Command] = List(
    DefineFun(FunDef(xs, List.empty, IntSort(), zero)),
    DefineFun(FunDef(ys, List.empty, IntSort(), Neg(one)))
  )

  val badExpr: Term =
    and(
      GreaterEquals(x,three),
      LessThan(x,zero)
    )

  /** This is an example SMT script (as a sequence of commands, which
    * are given to the interpreter one by one) 
    * 
    * On the left is the command, and on the right is the response
    * that is received.
    */
  val testCmds = Table(
    "command" -> "response",
    DeclareConst(xs,IntSort()) -> Success,
    DeclareConst(ys,IntSort()) -> Success,

    Push(0) -> Success,
    Assert(goodExpr) -> Success,
    CheckSat() -> CheckSatStatus(SatStatus),
    GetModel() -> GetModelResponseSuccess(goodModel),
    Pop(0) -> Success,

    Push(0) -> Success,
    Assert(badExpr) -> Success,
    CheckSat() -> CheckSatStatus(UnsatStatus),
    GetModel() -> Error("line 23 column 10: model is not available"),
    Pop(0) -> Success
  )

}
