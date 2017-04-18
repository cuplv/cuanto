package edu.colorado.plv.cuanto

import scala.util.parsing.input.Positional

package jsy {

  /** Expressions ''e''. */
  trait Expr extends Positional

  case class Unary(op: Uop, e1: Expr) extends Expr
  case class Binary(op: Bop, e1: Expr, e2: Expr) extends Expr

  /** Unary operators ''uop''. */
  trait Uop {
    def apply(e1: Expr): Expr = Unary(this, e1)
  }

  /** Binary operators ''bop''. */
  trait Bop {
    def apply(e1: Expr, e2: Expr): Expr = Binary(this, e1, e2)
  }

  /** Values ''v''. */
  trait Val extends Expr

}

/** Defining the JavaScripty language platform.
  *
  * ==Design Principles==
  *
  * JavaScripty is defined through a number of sub-language packages to
  * facilitate instantiations that contain only particular sub-languages.
  *
  * The sub-languages are organized so that we can design experimental
  * program analyses incrementally on small, well-defined sub-languages.
  *
  * This goal does come with a cost: the language platform is more complex
  * from abstract syntax representation to parsing to interpreting.
  *
  * We pose some design principles:
  *
  *   - To try to limit excessive generalization, we do not aim to support
  * any combination of language modules. We focus on commonly-used
  * sub-languages (e.g., numerical constraints, a core λ-calculus,
  * a core first-order imperative language).
  *   - We limit the syntactic stratification and say that programs are
  * simply expressions and all language constructs are expressions.
  *   - We emphasize as simple and clean semantics as possible (with
  * static typing).
  *   - For convenience in writing programs, we aim to be compatible
  * with concrete syntax of JavaScript, but we do not aim for
  * semantic compatibility.
  *
  * ==This Package==
  *
  * This top-level package defines the abstract syntax nodes that
  * are common to many sub-languages.
  *
  * ==Abstract Syntax==
  *
  * As a notation convenience, we write productions with concrete and
  * abstract syntax using ≡ at the meta-level to separate concrete and
  * abstract syntax.
  *
  *   - ''e'' ϵ `Expr` ::= ''v''
  *     | $jsyUnaryProduction
  *     | $jsyBinaryProduction
  *   - ''uop'' ϵ `Uop`
  *   - ''bop'' ϵ `Bop`
  *   - ''v'' ϵ `Val`
  *
  * @define jsyUnaryProduction ''uop'' ''e,,1,,'' ≡ `Unary(''uop'', ''e,,1,,'')`
  * @define jsyBinaryProduction ''e,,1,,'' ''bop'' ''e,,2,,'' ≡ `Binary(''bop'', ''e,,1,,'', ''e,,2,,'')`
  *
  * @author Bor-Yuh Evan Chang
  */
package object jsy

