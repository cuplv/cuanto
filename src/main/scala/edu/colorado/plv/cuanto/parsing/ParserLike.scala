package edu.colorado.plv.cuanto.parsing

import java.io.{File, FileInputStream, InputStream, InputStreamReader}

import scala.util.Try
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input._

/** Mix-in to define a convenient interface for parsers.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike[+T] { _: Parsers =>

  /** Define the scanner. */
  protected def scan(in: Reader[Char]): Input

  /** Define the start symbol. */
  def start: Parser[T]

  /** Parse a complete phrase using `start`. */
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
