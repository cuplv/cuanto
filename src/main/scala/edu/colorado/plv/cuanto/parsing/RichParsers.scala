package edu.colorado.plv.cuanto.parsing

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.Position

/** Mix in some utilities for [[scala.util.parsing.combinator.Parsers]].
  *
  * @author Bor-Yuh Evan Chang
  */
trait RichParsers extends Parsers {

  /** Helper parser to expose the position. */
  def withpos[A](q: => Parser[A]): Parser[(Position, A)] = Parser { in =>
    q(in) map { t => (in.pos,t) }
  }

  /** Pairs. */
  def pairsep[A,B](a: => Parser[A], sep: => Parser[Any], b: => Parser[B]): Parser[(A,B)] = {
    a ~ (sep ~> b) ^^ { case a ~ b => (a, b) }
  }
  def pairoptsep[A,B](a: => Parser[A], sep: => Parser[Any], b: => Parser[B]): Parser[(A,Option[B])] = {
    a ~ opt(sep ~> b) ^^ { case a ~ b => (a, b) }
  }

}

