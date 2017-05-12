package edu.colorado.plv.cuanto.jsy

import edu.colorado.plv.cuanto.jsy.binding.Mode

package imp {

  /** Mode `var`. Mutable variables.
    *
    * @group Abstract Syntax Nodes
    */
  case object MVar extends Mode

}

/** The Imp subset of JavaScripty.
  *
  * Mixes [[numerical]] and [[mutation]].
  *
  * @author Bor-Yuh Evan Chang
  */
package object imp
