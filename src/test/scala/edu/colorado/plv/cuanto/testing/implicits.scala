package edu.colorado.plv.cuanto.testing

import org.scalactic.Equality

import scala.util.Try

/**
  * @author Bor-Yuh Evan Chang
  */
object implicits {

  implicit def tryEquality[T]: Equality[Try[T]] = { (a: Try[T], b: Any) =>
    Try(a.get == b.asInstanceOf[T]).getOrElse(false)
  }

}
