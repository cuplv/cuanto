package edu.colorado.plv.cuanto.parsing

import java.io.{File, FileInputStream, InputStream, InputStreamReader}

import scala.util.Try
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input._

/** Represent an error with location information. */
class PositionedError(kind: String, msg: String, source: String, pos: Position) extends Exception {
  override def toString =
    if (pos != NoPosition)
      "%s\n%s:%s:%s: %s\n%s".format(kind, source, pos.line, pos.column, msg, pos.longString)
    else
      "%s\n%s: %s".format(kind, source, msg)
}

/** Mix-in to define a convenient interface for parsers.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike[+T] { self: Parsers =>
  /** Define the scanner. */
  protected def scan(in: Reader[Char]): Input

  /** Define the start symbol. */
  def start: Parser[T]

  /** A syntax error exception. */
  case class SyntaxError(msg: String, source: String, pos: Position) extends PositionedError("SyntaxError", msg, source, pos)

  /** Parse a complete phrase using [[start]]. */
  def parse(in: Input, source: String): Try[T] = Try {
    phrase(start)(in) match {
      case Success(t, _) => t
      case NoSuccess(msg, next) => throw SyntaxError(msg, source, next.pos)
    }
  }

  /** Parse a complete phrase from `in`: [[java.lang.CharSequence]]. */
  def parse(in: CharSequence): Try[T] =
    parse(scan(new CharSequenceReader(in)), "<string>")

  /** Parse a complete phrase from `in`: [[java.io.InputStream]]. */
  def parse(in: InputStream, source: String = "<stream>"): Try[T] = {
    val reader = StreamReader(new InputStreamReader(in))
    parse(scan(reader), source)
  }

  /** Parse a complete phrase from `in`: [[java.io.File]]. */
  def parse(in: File): Try[T]  =
    parse(new FileInputStream(in), in.getName)
}
