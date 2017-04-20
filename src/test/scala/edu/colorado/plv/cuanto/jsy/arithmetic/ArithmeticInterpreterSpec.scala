package edu.colorado.plv.cuanto.jsy
package arithmetic

import edu.colorado.plv.cuanto.CuantoSpec

import scala.util.Try

/**
  * @author Bor-Yuh Evan Chang
  */
class ArithmeticInterpreterSpec extends CuantoSpec with ArithmeticInterpreterBehaviors {
  import SimpleInterpreter._

  behavior of "denote"

  it should behave like interpreter(e => Try(denote(e)))

  behavior of "bigstep"

  it should behave like interpreter(e => Try { val N(n) = bigstep(e); n })

  behavior of "smallstep"

  it should behave like interpreter(e => Try {
    val N(n) = iterate[Expr](e)(smallstep)
    n
  })

  behavior of "machine"

  def evalMachine(e: Expr): Expr = {
    val id: Cont = identity
    val eq: ((Expr,Cont), (Expr,Cont)) => Boolean = { case ((e1,_), (e2, _)) => e1 == e2 }
    val (ep, _) = postfixedpoint( (e,id) )( machine )( eq )
    ep
  }

  it should behave like interpreter(e => Try {
    val N(n) = evalMachine(e)
    n
  })

  behavior of "Denotational.Concrete"

  it should behave like interpreter(e => Try(Denotational.Concrete(e)))

}
