package edu.colorado.plv.cuanto.jsy

package boolean {

  /* Literals and Values */

  /** @group Abstract Syntax Nodes */
  case class B(b: Boolean) extends Val

  /* Operators */

  /** @group Abstract Syntax Nodes */
  case object Not extends Uop
  /** @group Abstract Syntax Nodes */
  case object And extends Bop
  /** @group Abstract Syntax Nodes */
  case object Or extends Bop
  /** @group Abstract Syntax Nodes */
  case class If(e1: Expr, e2: Expr, e3: Expr) extends Expr

}

/** Define the Boolean sub-language.
  *
  * This package defines the abstract syntax nodes for Boolean expressions.
  *
  * ==Abstract Syntax==
  *
  *   - ''v'' ::= ''b'' ≡ `B(''b'')`
  *   - ''uop'' ::= `!` ≡ `Not`
  *   - ''bop'' ::= `&&` ≡ `And` | `||` ≡ `Or`
  *   - ''e'' ::=  `if` `(''e,,1,,'')` ''e,,2,,'' `else` ''e,,3,,'' ≡ `If(''e,,1,,'', ''e,,2,,'', ''e,,3,,'')`
  *   - ''b'' ϵ `Boolean` ::= `true` | `false`
  *
  * @author Bor-Yuh Evan Chang
  */
package object boolean
