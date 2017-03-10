package edu.colorado.plv.cuanto.recursing

import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks

/**
  * @author Bor-Yuh Evan Chang
  */
class FixFunSpec extends FlatSpec with Matchers with PropertyChecks {

  /* A mock language. */

  trait Expr
  case class N(n: Int) extends Expr
  case class Neg(e1: Expr) extends Expr

  object negexpr {
    def pretty: FixFun[Expr, String] = FixFun(self => {
      case N(n) => n.toString
      case Neg(e1) => s"(-${self(e1)})"
    })

    def eval: FixFun[Expr, Int] = FixFun(self => {
      case N(n) => n
      case Neg(e1) => -self(e1)
    })
  }

  /* A mock language extension. */

  case class Plus(e1: Expr, e2: Expr) extends Expr

  object plusexpr {
    def pretty: FixFun[Expr, String] = negexpr.pretty extendWith FixFun(self => {
      case Plus(e1, e2) => s"(${self(e1)} + ${self(e2)})"
    })

    def eval: FixFun[Expr, Int] = negexpr.eval extendWith FixFun(self => {
      case Plus(e1, e2) => self(e1) + self(e2)
    })

    def prettyeval: FixFun[Expr, Int] =
      eval map { eval => e =>
        println(s"eval(${pretty(e)})")
        eval(e)
      }
  }

  /* Some expressions. */

  val exprmap: Map[Expr,(Int,String)] = Map(
    N(1) -> (1, "1"),
    Neg(N(2)) -> (-2, "(-2)"),
    Plus(N(1), N(2)) -> (3, "(1 + 2)"),
    Neg(Plus(N(2), N(3))) -> (-5, "(-(2 + 3))")
  )

  val evalTable = Table("expr" -> "int", (exprmap mapValues { _._1 }).toSeq: _*)
  val prettyTable = Table("abstract" -> "concrete", (exprmap mapValues { _._2 }).toSeq: _*)

  behavior of "plusexpr.pretty"

  forAll (prettyTable) { (e, s) =>
    it should s"pretty-print ${e} to ${s}" in {
      plusexpr.pretty(e) shouldEqual s
    }
  }

  behavior of "plusexpr.eval"

  forAll (evalTable) { (e, i) =>
    it should s"evaluate ${e} to ${i}" in {
      plusexpr.eval(e) shouldEqual i
    }
  }

  def nums: Gen[Expr] = Arbitrary.arbitrary[Int] map N
  def negs(exprs: Gen[Expr]): Gen[Neg] = exprs map Neg
  def plus(exprs: Gen[Expr]): Gen[Plus] = for { e1 <- exprs; e2 <- exprs } yield Plus(e1,e2)
  def exprs: Gen[Expr] = Gen.lzy(Gen.oneOf(nums, negs(exprs), plus(exprs)))

  it should "not crash on arbitrary expressions" in {
    forAll (exprs) { (e: Expr) =>
      plusexpr.eval(e)
    }
  }

  def negexprs: Gen[Expr] = Gen.lzy(Gen.oneOf(nums, negs(negexprs)))

  "negexpr.eval" should "not crash on arbitrary negexpr expressions" in {
    forAll (negexprs) { (e: Expr) =>
      negexpr.eval(e)
    }
  }
}
