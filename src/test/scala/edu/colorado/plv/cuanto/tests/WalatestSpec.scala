package edu.colorado.plv.cuanto
package tests

import java.net.URL

import com.typesafe.scalalogging.LazyLogging

/**
  * @author Bor-Yuh Evan Chang
  */
class WalatestSpec extends CuantoSpec with LazyLogging {
  import testing.implicits._

  behavior of "Walatest"

  it should "should refer to a valid URLs" in {
    for {
      (f, v) <- Walatest.asTraversable[URL]
    } logger.info(s"${f} = ${v}")
  }

}
