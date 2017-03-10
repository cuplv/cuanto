import edu.colorado.plv.cuanto.recursing.FixFun

trait Expr

case class N(n: Int) extends Expr
case class Neg(e1: Expr) extends Expr

def pretty1: FixFun[Expr,String] = FixFun(self => {
  case N(n) => n.toString
  case Neg(e1) => s"(-${self(e1)})"
})

def eval1: FixFun[Expr,Int] = FixFun(self => {
  case N(n) => n
  case Neg(e1) => -self(e1)
})

case class Plus(e1: Expr, e2: Expr) extends Expr

def pretty2: FixFun[Expr,String] = pretty1 orElse FixFun(self => {
  case Plus(e1, e2) => s"(${self(e1)} + ${self(e2)})"
})

def eval2: FixFun[Expr,Int] = eval1 orElse FixFun(self => {
  case Plus(e1,e2) => self(e1) + self(e2)
})

def prettyeval2: FixFun[Expr,Int] =
  eval2 compose { (e: Expr) => println(s"eval(${pretty2(e)})"); e }

val a = N(1)
val b = Neg(a)
val c = Plus(a,b)
val d = Neg(Plus(N(2),N(3)))

pretty1(a)
pretty2(b)
pretty2(c)
pretty2(d)

eval1(a)
eval1(b)
eval2(b)
eval2(c)
eval2(d)

prettyeval2(d)
