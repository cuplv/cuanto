package edu.colorado.plv.cuanto.tests

import java.net.URL

import edu.colorado.plv.cuanto.jutil.implicits._

/** This object lists test fixtures corresponding to edu.colorado.plv.cuanto.walatest._
  *
  * @author Bor-Yuh Evan Chang
  */
object Walatest {
  import edu.colorado.plv.cuanto.walatest._
  lazy val emptyMainTestURL: URL = classOf[EmptyMainTest].getRelativeURL.get
}

