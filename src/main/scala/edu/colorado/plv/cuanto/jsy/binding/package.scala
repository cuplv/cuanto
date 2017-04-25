package edu.colorado.plv.cuanto.jsy

import scala.util.parsing.input.Positional

package binding {

  /** @group Abstract Syntax Nodes */
  case object Unit extends Val

  /** Let-binding.
    *
    * @param m the mode (see [[Mode]])
    * @param x the variable
    * @param e1 the binding expression
    * @param e2 the continuation expression
    *
    * @group Abstract Syntax Nodes
    */
  case class Bind(m: Mode, x: Var, e1: Expr, e2: Expr) extends Expr

  /** Binding mode.
    *
    * In JavaScript, variables may be immutable or mutable.
    *
    * @group Abstract Syntax Nodes
    */
  trait Mode extends Positional

  /** Mode `const`. Immutable variables.
    *
    * @group Abstract Syntax Nodes
    */
  case object MConst extends Mode

  /** Sequencing.
    *
    * @group Abstract Syntax Nodes
    */
  case object Seq extends Bop

}

/** Define a sub-language with variables and binding.
  *
  * ==Abstract Syntax==
  *
  *   - ''v'' ::= `undefined` ≡ `Unit`
  *   - ''e'' ::= ''x'' | `let` ''x'' `=` ''e,,1,,'' `in` ''e,,2,,'' ≡ `Bind(''x'',''e,,1,,'',e,,2,,)`
  *             | ''e,,1,,'' , ''e,,2,,'' ≡ `Binary(Seq,''e,,1,,'',''e,,2'')`
  *   - ''x'' ϵ `Var`
  *
  * @author Kyle Headley
  * @author Bor-Yuh Evan Chang
  */
package object binding
