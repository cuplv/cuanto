package edu.colorado.plv.cuanto.numdomain
import apron.{Box, _}
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Tianhan Lu
  */
class NumDomainSpec extends FlatSpec with Matchers {
  "Constructing a new domain in Apron" should "not crash" in {
    val box = Array(new Interval(1, 2), new Interval(-3, 5), new Interval(3, 4, 6, 5))
    new Abstract0(new Box, 2, 1, box)
  }
}
