package edu.colorado.plv.cuanto.jutil

import java.net.URL

/** Top-level implicits for some Java utility conversions.
  *
  * @author Bor-Yuh Evan Chang
  */
object implicits {

  /** Returns the [[URL]] for the location where the given class is defined.
    *
    * @param c the [[Class]] object
    */
  implicit def URLofClass[T](c: Class[T]): URL = c.getResource(".")

}
