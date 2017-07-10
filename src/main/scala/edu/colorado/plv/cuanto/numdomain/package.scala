package edu.colorado.plv.cuanto

import gmp.Mpfr

/**
  * Created by Tianhan Lu on 4/20/17.
  */
package object numdomain {
  trait Bop
  trait Cop

  case object ADD extends Bop {
    override def toString: String = "+"
  }
  case object SUB extends Bop {
    override def toString: String = "-"
  }
  case object MUL extends Bop {
    override def toString: String = "*"
  }
  case object DIV extends Bop {
    override def toString: String = "/"
  }
  case object MOD extends Bop {
    override def toString: String = "%"
  }
  case object POW extends Bop {
    override def toString: String = "^"
  }

  case object LE extends Cop {
    override def toString: String = "<="
  }
  case object LT extends Cop {
    override def toString: String = "<"
  }
  case object GE extends Cop {
    override def toString: String = ">="
  }
  case object GT extends Cop {
    override def toString: String = ">"
  }
  case object NE extends Cop {
    override def toString: String = "!="
  }
  case object EQ extends Cop {
    override def toString: String = "=="
  }

  val ROUNDING = Mpfr.RNDU
}
