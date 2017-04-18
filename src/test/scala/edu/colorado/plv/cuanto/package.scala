package edu.colorado.plv

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks

/**
  * @author Bor-Yuh Evan Chang
  */
package cuanto {

  /** Create a common scalatest spec trait for convenience. */
  trait CuantoSpec extends FlatSpec with Matchers with PropertyChecks

}
