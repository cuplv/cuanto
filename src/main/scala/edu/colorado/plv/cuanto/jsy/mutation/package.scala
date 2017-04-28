package edu.colorado.plv.cuanto.jsy

package mutation {

  /** @group Abstract Syntax Nodes */
  case object Assign extends Bop

  /** @group Abstract Syntax Nodes */
  case object Null extends Val

  /** @group Intermediate AST Nodes */
  case object Deref extends Uop



}


/**
  * @author Bor-Yuh Evan Chang
  */
package object mutation
