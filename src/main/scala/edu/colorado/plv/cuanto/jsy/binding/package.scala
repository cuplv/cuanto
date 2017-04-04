package edu.colorado.plv.cuanto.jsy

package binding {

  /** @group Abstract Syntax Nodes */
  case object Unit extends Val

  /** A Var node in an expression is a variable use.
    *
    * The name of the variable is stored privately.
    *
    * @group Abstract Syntax Nodes
    */
  case class Var(private val x: String) extends Expr

  /** Let-binding.
    *
    * @param x the variable
    * @param e1 the binding expression
    * @param e2 the continuation expression
    *
    * @group Abstract Syntax Nodes
    */
  case class Bind(x: Var, e1: Expr, e2: Expr) extends Expr

  case object Seq extends Bop

}

/** Define a sub-language with variables and binding.
  *
  * ==Abstract Syntax==
  *
  *   - ''v'' ::= `undefined` ≡ `Unit`
  *   - ''e'' ::= ''x'' | `let` ''x'' `=` ''e,,1,,'' `.` ''e,,2,,'' ≡ `Bind(''x'',''e,,1,,'',e,,2,,)`
  *             | ''e,,1,,'' , ''e,,2,,'' ≡ `Binary(Seq,''e,,1,,'',''e,,2'')`
  *   - ''x'' ϵ `Var`
  *
  * @author Kyle Headley
  * @author Bor-Yuh Evan Chang
  */
package object binding
