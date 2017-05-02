package edu.colorado.plv.cuanto.jsy

/** The skeleton of a Thresher-style refinement analysis.
  *
  * Main entrypoint is JsyThresher#canRefute
  *
  * Programs are assumed to be from a restricted fragment of the language:
  *
  * Prgm ::= Stmt | Prgm ; Prgm | if(Atom) Prgm else Prgm
  * Stmt ::= x := Atom | x := Atom Bop Atom
  * Atom ::= Var | Bool | Num
  * Bop ::= + | - | * | /
  *       | && | ||
  *       | == | != | <= | < | >= | >
  *
  *
  * @author
  */
package object refutation {

  /** helper function returns the set of Variables contained in an expression */
  def getVars(e : Expr): Set[Var] = e match {
    case Binary(_, l, r) => getVars(l) union getVars(r)
    case v:Var => Set(v)
    case boolean.If(cond,t,f) => getVars(cond) union getVars(t) union getVars(f)
    case _ => Set()
  }
}
