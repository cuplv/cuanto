package edu.colorado.plv.cuanto.jutil

import edu.colorado.plv.cuanto.jutil.lang.PimpClass

/**
  * @author Bor-Yuh Evan Chang
  */
object implicits {
  implicit def pimpClass[C](c: Class[C]): PimpClass[C] = new PimpClass[C](c)
}
