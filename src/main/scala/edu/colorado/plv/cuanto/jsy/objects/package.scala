package edu.colorado.plv.cuanto.jsy

package objects {

  /** @group Abstract Syntax Nodes */
  case class Obj(fields: Map[Property, Val]) extends Expr

  /** @group Abstract Syntax Nodes */
  case class GetProp(prop: Property) extends Uop

  /** @group Abstract Syntax Nodes */
  trait Property

  /** Field.
    *
    * @group Abstract Syntax Nodes */
  case class Fld(f: String) extends Property

  /** Dynamic property.
    *
    * @group Abstract Syntax Nodes
    */
  case class Idx(e: Expr) extends Property

}

/**
  * @author Bor-Yuh Evan Chang
  */
package object objects
