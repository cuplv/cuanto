package edu.colorado.plv.cuanto
package abstracting.symbolic

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks

class SymbolicAbstractionSpec extends FlatSpec with Matchers with PropertyChecks {
  import BoolVote._

  "The postHat funtion" should "correctly model voteYay" in {
    postHatUp(voteYayT, Top) should equal (voteYay(Top))
    postHatUp(voteYayT, Yay) should equal (voteYay(Yay))
    postHatUp(voteYayT, Nay) should equal (voteYay(Nay))
    postHatUp(voteYayT, Bot) should equal (voteYay(Bot))
  }

  "The postHat funtion" should "correctly model voteNay" in {
    postHatUp(voteNayT, Top) should equal (voteNay(Top))
    postHatUp(voteNayT, Yay) should equal (voteNay(Yay))
    postHatUp(voteNayT, Nay) should equal (voteNay(Nay))
    postHatUp(voteNayT, Bot) should equal (voteNay(Bot))
  }
}
    
