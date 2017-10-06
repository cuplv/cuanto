package edu.colorado.plv.cuanto.testing

import org.scalactic.Equality

import scala.util.Try

/** Defines convenience conversions that should be solely used in test code.
  *
  * @author Bor-Yuh Evan Chang
  */
object implicits {

  /** Implicitly compare a value of type [[Try]]`[T]` with a value of type `T`. */
  implicit def tryEquality[T]: Equality[Try[T]] = { (a: Try[T], b: Any) =>
    Try(a.get == b.asInstanceOf[T]).getOrElse(false)
  }

  /** Make a ''fixture'' object traversable.
    *
    * For our purposes, a ''fixture'' object is defined an object with
    * some `lazy val` declarations.
    *
    * @param f the fixture object
    * @tparam F tye type of the fixture object
    */
  implicit class TraversableFixture[F](f: F)(implicit fctag: scala.reflect.ClassTag[F]) {
    import scala.reflect.runtime.{universe => ru}

    /** The reflection mirror for `f`. */
    private[this] lazy val omirror = scala.reflect.runtime.currentMirror.reflect(f)

    /** Makes the `lazy val`s of type `T` as a traversable sequence (via run-time reflection). */
    def asTraversable[T](implicit ftag: ru.TypeTag[F], ttag: ru.TypeTag[T]): Traversable[(ru.Symbol,T)] = {
      val typeF = ru.typeOf[F]
      val typeT = ru.typeOf[T]
      typeF.decls filter { d =>
        val tryIsTypeT = for {
          d <- Try(d.asTerm)
          dtype = d.infoIn(typeF)
        } yield d.isLazy && dtype.finalResultType =:= typeT
        tryIsTypeT getOrElse false
      } map { d =>
        val v = (omirror reflectMethod d.asMethod)()
        (d, v.asInstanceOf[T])
      }
    }
  }

}
