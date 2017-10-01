package edu.colorado.plv.cuanto.jutil.lang

import java.net.URL

/**
  * @author Bor-Yuh Evan Chang
  */
class PimpClass[C](c: Class[C]) {

  /** Returns the [[URL]] for the location where the given class is defined. */
  def getRelativeURL: Option[URL] = Option(c.getResource("."))

  /** Returns the [[URL]] for the top-level location where the given class is defined. */
  def getAbsoluteURL: Option[URL] = Option(c.getResource("/"))

}
