package edu.colorado.plv.cuanto.jsy.refutation

import edu.colorado.plv.cuanto.jsy._

object JsyThresher{

  /** Main entrypoint: given some constraints and a program, is the endpoint of that program reachable with those constraints */
  def canRefute(init: AState, program: Expr) : Boolean = stepThrough(init, program)  forall {st => !st.satisfiable(entry=true)}

  private def stepThrough(init: AState, program: Expr) : Iterable[AState] = program match {
    case Binary(binding.Sequ, e1, boolean.If(cond, t_branch, f_branch)) =>
      ((stepThrough(init, t_branch) map {st => st addPure cond}) ++
        (stepThrough(init, f_branch) map {st => st addPure Unary(boolean.Not, cond)})) flatMap {st => stepThrough(st, e1)}

    case Binary(binding.Sequ, e1, e2) => step(e2)(init) flatMap { st => stepThrough(st, e1) }

    case e if isStmt(e) => step(e)(init)
  }

  /** Transformer for abstract states */
  private def step(expr: Expr)(st: AState): Iterable[AState] = {
    assert(isStmt(expr), s"Expression $expr is not a valid statement for JSY-Thresher.")
    expr match {
      case Binary(mutation.Assign, v:Var, rhs) => st.write(v,rhs)
      case _ => sys.error(s"This case should never be reached, since isStmt($expr)==true")
    }
  }

  /** Returns true iff e is a legal statement in this refuter's target language*/
  private def isStmt(e: Expr) : Boolean = e match {
    case Binary(mutation.Assign,v:Var, rhs) => rhs match {
      case a if isAtom(a) => true
      case Binary(op,l,r) => isNumericalOp(op) && isAtom(l) && isAtom(r)
    }
    //TODO(benno): add heap reads/writes as legal statements
    case _ => false
  }
  /** Returns true iff e is a constant or variable */
  private def isAtom(e: Expr) : Boolean = e.isInstanceOf[boolean.B] || e.isInstanceOf[arithmetic.N] || e.isInstanceOf[Var]

  /** limit the set of legal binary operations in rval positions */
  private def isNumericalOp(op: Bop):Boolean = op match {
    case boolean.And | boolean.Or
         | numerical.Eq | numerical.Ge | numerical.Gt | numerical.Le | numerical.Lt | numerical.Ne
         | arithmetic.Div | arithmetic.Minus | arithmetic.Plus | arithmetic.Times
    => true
    case _ => false
  }
}
