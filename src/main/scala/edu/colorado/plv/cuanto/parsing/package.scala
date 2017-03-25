package edu.colorado.plv.cuanto

import scala.util.parsing.input.{NoPosition, Position}

/**
  * @author Bor-Yuh Evan Chang
  */
package parsing {

  /** Represent an error with location information. */
  class PositionedError(kind: String, msg: String, source: String, pos: Position) extends Exception {
    override def toString =
      if (pos != NoPosition)
        "%s\n%s:%s:%s: %s\n%s".format(kind, source, pos.line, pos.column, msg, pos.longString)
      else
        "%s\n%s: %s".format(kind, source, msg)
  }

  /** A syntax error exception. */
  case class SyntaxError(msg: String, source: String, pos: Position) extends PositionedError("SyntaxError", msg, source, pos)

}
