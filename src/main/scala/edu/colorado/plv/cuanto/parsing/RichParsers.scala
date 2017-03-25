package edu.colorado.plv.cuanto.parsing

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.Position

/** Mix in some utilities for [[scala.util.parsing.combinator.Parsers]].
  *
  * @author Bor-Yuh Evan Chang
  */
trait RichParsers extends Parsers {

  /** Helper parser to expose the position. */
  def withpos[T](q: => Parser[T]): Parser[(Position, T)] = Parser { in =>
    q(in) map { t => (in.pos,t) }
  }

}

