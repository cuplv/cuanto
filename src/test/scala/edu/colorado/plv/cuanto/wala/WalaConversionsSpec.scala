package edu.colorado.plv.cuanto.wala

import com.ibm.wala.types.{Descriptor, TypeReference}
import com.ibm.wala.util.strings.Atom
import org.scalatest.FlatSpec

/**
  * @author Bor-Yuh Evan Chang
  */
class WalaConversionsSpec extends FlatSpec {
  import WalaConversions._

  "stringToAtom" should "return the same Atom for the same string" in {
    assert {
      val a: Atom = "def"
      val b: Atom = "def"
      a == b
    }
  }

  val JavaLangStringName = "Ljava/lang/String"
  "stringToTypeName" should s"on ${JavaLangStringName} return the expected TypeName" in {
    assertResult(TypeReference.JavaLangString.getName) {
      stringToTypeName(JavaLangStringName)
    }
  }

  "typenamesToDescriptor" should "" in {
    val direct = Descriptor.findOrCreateUTF8("()V")
    assertResult(direct) {
      typenamesToDescriptor(Seq() -> "V")
    }
  }

}

