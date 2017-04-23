package edu.colorado.plv.cuanto.jsy

package functions {

  /** @group Abstract Syntax Nodes */
  case class Fun(p: Option[Var], parameters: List[(Var, Option[Typ])], tann: Option[Typ], e0: Expr) extends Val

  /** @group Abstract Syntax Nodes */
  case class Call(e0: Expr, arguments: List[Expr]) extends Expr

  /** @group Abstract Syntax Nodes */
  case class TFun(parameters: List[(Var, Option[Typ])], t: Typ) extends Typ

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
package object functions
