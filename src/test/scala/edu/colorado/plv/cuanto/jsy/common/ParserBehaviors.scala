package edu.colorado.plv.cuanto
package jsy
package common

import org.scalatest.prop.{TableFor1, TableFor2}

import scala.util.Try

/**
  * @author Bor-Yuh Evan Chang
  */
trait ParserBehaviors { _: CuantoSpec =>

  type PositiveTable = TableFor2[String,Expr]
  type NegativeTable = TableFor1[String]

  /** Parameter: specify the positive tests. */
  lazy val positives: PositiveTable = Table("concrete" -> "abstract")

  /** Parameter: specify the flexible tests. */
  lazy val flexibles: PositiveTable = Table("concrete" -> "abstract")

  /** Parameter: specify the negative tests. */
  lazy val negatives: NegativeTable = Table("concrete")

  def parser(parse: String => Try[Expr]): Unit = {
    import testing.implicits.tryEquality
    forAll(positives) { (conc, abs) =>
      it should s"parse $conc into $abs" in {
        parse(conc) shouldEqual abs
      }
    }
    forAll(flexibles) { (conc, abs) =>
      it should s"flexibly parse $conc into $abs" in {
        parse(conc) shouldEqual abs
      }
    }
    forAll(negatives) { conc =>
      it should s"not parse $conc" in {
        parse(conc) should be a 'failure
      }
    }
  }

}
