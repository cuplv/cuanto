package edu.colorado.plv.cuanto.jsy

package functions {

  /** @group Abstract Syntax Nodes */
  case class Fun(p: Option[Var], parameters: Parameters, tann: Option[Typ], e0: Expr) extends Val

  /** @group Abstract Syntax Nodes */
  case class Call(e0: Expr, arguments: List[Expr]) extends Expr

  /** @group Abstract Syntax Nodes */
  case class TFun(parameters: Parameters, t: Typ) extends Typ

  /** @group Abstract Syntax Nodes */
  case object Return extends Uop

}

/** Define the functions sub-language.
  *
  * This package defines the abstract syntax nodes for function expressions.
  *
  * ==Abstract Syntax==
  *
  *   - ''v'' ::= `''p''(''parameters'') ''tann'' => ''e,,0,,''` ≡ `Fun(''p'',''parameters'', ''tann'', ''e,,0,,'')`
  *   - ''p'' ::= ε | ''x''
  *   - ''parameters'' ::= ε | ''parameters'', `''x'' : ''τ''` | ''parameters'', ''x''
  *   - ''tann'' ::= ε | `: ''τ''`
  *   - ''e'' ::=  `''e,,0,,''(''arguments'')`
  *   - ''arguments'' ::= ε | ''arguments'', ''e''
  *
  * @author Bor-Yuh Evan Chang
  */
package object functions {

  type Parameters = List[(Var, Option[Typ])]

}
