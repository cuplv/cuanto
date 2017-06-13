package edu.colorado.plv.cuanto.wala

import scala.language.implicitConversions
import java.net.URL

import com.ibm.wala.types.{Descriptor, TypeName}
import com.ibm.wala.util.strings.Atom

/** Defines a collection of convenience conversions for WALA data structures.
  *
  * @author Bor-Yuh Evan Chang
  */
object WalaConversions {

  /** Returns the [[URL]] for the location where the given class is defined.
    *
    * @param c the [[Class]] object
    */
  implicit def classURL[T](c: Class[T]): URL = c.getResource(".")

  /** Returns the [[Atom]] for a given [[String]].
    *
    * @param s the [[String]]
    */
  implicit def stringToAtom(s: String): Atom = Atom.findOrCreateUnicodeAtom(s)

  /** Returns the [[TypeName]] for a given fully qualified class name.
    *
    * @param name the class name
    */
  implicit def stringToTypeName(name: String): TypeName = TypeName.findOrCreate(name)

  /** Returns the [[Descriptor]] for a method signature as [[TypeName]]s
    *
    * @param typenames the method signature
    */
  implicit def typenamesToDescriptor(typenames: (Seq[TypeName], TypeName)): Descriptor = {
    val (params, ret) = typenames
    Descriptor.findOrCreate(params.toArray, ret)
  }

}
