package edu.colorado.plv.cuanto.numdomain

import apron.{Box, Manager, Octagon, Polka, _}
import gmp.Mpfr
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by lumber on 4/20/17.
  */
class NumDomainSpec extends FlatSpec with Matchers {
  runAPITest()

  def runAPITest(): Unit = {
    println("")
    println("")
    println("Box")
    println("=========")
    test(new Box)

    println("")
    println("Octagons")
    println("=========")
    test(new Octagon)

    println("")
    println("Polyhedra (closed convex polyhedra)")
    println("=========")
    test(new Polka(false))
    println("")
    println("Polyhedra (non-closed convex polyhedra)")
    println("=========")
    test(new Polka(true))
  }

  private def test(man: Manager): Unit = {
    /* build some expressions */

    /* level 0 */
    val box = Array(new Interval(1, 2), new Interval(-3, 5), new Interval(3, 4, 6, 5))
    val ltrms = Array(new Linterm0(1, new MpqScalar(-5)), new Linterm0(0, new Interval(0.1, 0.6)), new Linterm0(2, new MpfrScalar(0.1, Mpfr.RNDU)))
    val linexp = new Linexpr0(ltrms, new MpqScalar(2))
    val ltrms2 = Array(new Linterm0(1, new MpqScalar(-5)), new Linterm0(0, new MpqScalar(1, 2)))
    val linexp2 = new Linexpr0(ltrms2, new MpqScalar(2))
    val txpr = new Texpr0BinNode(Texpr0BinNode.OP_ADD, new Texpr0BinNode(Texpr0BinNode.OP_MUL, new Texpr0DimNode(0), new Texpr0DimNode(1)), new Texpr0BinNode(Texpr0BinNode.OP_DIV, new Texpr0DimNode(2), new Texpr0CstNode(new MpqScalar(2))))
    val texp = new Texpr0Intern(txpr)
    val lincons = new Lincons0(Lincons0.SUPEQ, linexp)
    val lincons2 = new Lincons0(Lincons0.EQ, linexp2)
    val linconss = Array(lincons, lincons2)
    val tcons = new Tcons0(Tcons0.SUPEQ, texp)
    val gen = new Generator0(Generator0.RAY, linexp2)
    val gens = Array(gen)

    val chgtab = Array(0, 1, 2)
    val chg = new Dimchange(2, 1, chgtab)

    val permtab = Array(1, 0, 2)
    val perm = new Dimperm(permtab)

    val names = Array("me", "myself", "I")


    /* level 0 abstract elements */
    val full = new Abstract0(man, 2, 1)
    val empty = new Abstract0(man, 2, 1, true)
    val a0 = new Abstract0(man, 2, 1, box)
    println("full: " + full)
    println("empty: " + empty)
    println("a0: " + a0)
    println("a0: " + a0.toString(man, names))
    assert(!full.isBottom(man))
    assert(full.isTop(man))
    assert(empty.isBottom(man))
    assert(!empty.isTop(man))
    assert(!a0.isBottom(man))
    assert(!a0.isTop(man))
    assert(a0.isEqual(man, a0))
    assert(empty.isEqual(man, empty))
    assert(full.isEqual(man, full))
    assert(empty.isIncluded(man, a0))
    assert(a0.isIncluded(man, full))
    assert(a0.isIncluded(man, a0))
    assert(!a0.isIncluded(man, empty))
    assert(!full.isIncluded(man, a0))
    println("size: " + a0.getSize(man))
    println("hash: " + a0.hashCode(man))
    println("dim:  " + a0.getDimension(man))
    val man2 = a0.getCreationManager
    assert(man.getLibrary.equals(man2.getLibrary))
    a0.isBottom(man2)
    println("to-lcons: " + a0.toLincons(man).toString)
    println("to-box: " + a0.toBox(man).toString)
    println("to-tcons: " + a0.toTcons(man).toString)
    try
      println("to-gen: " + a0.toGenerator(man).toString)
    catch {
      case e: ApronException =>
        println("got exception: " + e)
    }
    println("bound 0:   " + a0.getBound(man, 0))
    println("bound lin: " + linexp + " -> " + a0.getBound(man, linexp))
    println("bound t:  " + texp + " -> " + a0.getBound(man, texp))
    println("sat lin:  " + a0.satisfy(man, lincons))
    println("sat t:    " + a0.satisfy(man, tcons))
    println("sat 0:    " + box(0) + " -> " + a0.satisfy(man, 0, box(0)))
    println("sat 1:    " + box(0) + " -> " + a0.satisfy(man, 1, box(0)))
    println("uncons 0: " + a0.isDimensionUnconstrained(man, 0))

    val a1 = new Abstract0(man, a0)
    assert(a0.isEqual(man, a1))
    a1.addRay(man, gen)
    println("+ gen: " + gen + " -> " + a1)
    assert(!a0.isEqual(man, a1))
    assert(a0.isIncluded(man, a1))
    assert(!a1.isIncluded(man, a0))
    assert(a1.isEqual(man, a0.addRayCopy(man, gen)))

    val ac = new Abstract0(man, a0)
    println("assign-lexp: " + a0.assignCopy(man, 0, linexp, null))
    println("assign-texp: " + a0.assignCopy(man, 0, texp, null))
    println("subst-lexp: " + a0.substituteCopy(man, 0, linexp, null))
    println("subst-texp: " + a0.substituteCopy(man, 0, texp, null))
    assert(a0.isEqual(man, ac))
    ac.assign(man, 0, linexp, null)
    assert(ac.isEqual(man, a0.assignCopy(man, 0, linexp, null)))
    assert(!ac.isEqual(man, a0))
    ac.assign(man, 0, texp, null)
    ac.substitute(man, 0, linexp, null)
    ac.substitute(man, 0, texp, null)


    assert(a0.meetCopy(man, full).isEqual(man, a0))
    assert(a0.joinCopy(man, empty).isEqual(man, a0))
    assert(a0.meetCopy(man, empty).isEqual(man, empty))
    assert(a0.joinCopy(man, full).isEqual(man, full))
    assert(a0.meetCopy(man, a0).isEqual(man, a0))
    assert(a0.joinCopy(man, a0).isEqual(man, a0))

    assert(a0.meetCopy(man, lincons).isIncluded(man, a0))
    assert(a0.meetCopy(man, lincons2).isIncluded(man, a0))
    assert(a0.meetCopy(man, tcons).isIncluded(man, a0))
    println("+ const: " + lincons2 + " -> " + a0.meetCopy(man, lincons2))
    val w = full.meetCopy(man, lincons2)
    println("widen: " + a0.widening(man, w))
    println("widen threshold: " + a0.wideningThreshold(man, w, linconss))
  }
}
