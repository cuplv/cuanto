package edu.colorado.plv.cuanto
package abstracting.symbolic

import abstracting.bitvectors.B3._

class SymbolicAbstractionSpec extends CuantoSpec {

  val avals = Seq(Top,Yay,Nay,Bot)

  "The postHat funtion" should "correctly model voteYay" in {
    avals.map({
      v => postHatUpInst(SMT3.voteYay,v) should equal (Vote3.voteYay(v))
    })
  }

  "The postHat funtion" should "correctly model voteNay" in {
    avals.map({
      v => postHatUpInst(SMT3.voteNay,v) should equal (Vote3.voteNay(v))
    })
  }

}
