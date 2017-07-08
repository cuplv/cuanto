package edu.colorado.plv.cuanto.numdomain

import apron.{Box, Manager, Octagon, Polka, _}
import edu.colorado.plv.cuanto.numdomain.apronapi._
import gmp.Mpfr
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by lumber on 4/20/17.
  */
class NumDomainSpec extends FlatSpec with Matchers {

  /**
    * java.library.path specifies the directories where System.loadLibrary() looks for the dynamic library file.
    * If you change the java.library.path system property in your code, it will not have any effect.
    * There are hacks to make Java "forget" the initial value and re-evaluate the contents of the java.library.path system property.
    *
    * However, the dependent library is not loaded by Java, it's loaded by Windows.
    * Windows does not care about java.library.path, it only cares about the PATH environment variable. Your only option is to adjust PATH for your Java process.
    * For example, if you start it from a batch file, change the PATH environment variable right before the java invocation.
    *
    *
    * http://stackoverflow.com/questions/12566732/java-jni-and-dependent-libraries-on-windows
    */

  /**
    * As a result, I set the current working directory to "/Users/lumber/Documents/workspace/cuanto/lib" in "Run -> Edit Configurations..."
    * However, this is only a temporary solution.
    */


  /**
    * 0. need to work on both local machine and travis...
    * 1. local machine: copy-paste libraries to cuanto/, then delete them
    * // 2. local machine: change working directory to cuanto/lib
    * 3. local machine: use `otool` https://stackoverflow.com/questions/17703510/dyld-library-not-loaded-reason-image-not-loaded
    */
  System.loadLibrary("jgmp")
  println("Working Directory = " + System.getProperty("user.dir"))
  System.loadLibrary("japron")
  runAPITest()
  // runInterfaceTest()

  // The point of having AbsTerm as parent class for ApronLinTerm and ApronNonLinTerm is to let them share a common interface such that some high-level procedures won't need to know the difference between them. Same for AbsExpr, AbsCons, AbsDom.
  def runInterfaceTest(): Unit = {
    val dom = new ApronDom((2, 1), Array(new ApronInterval(1, 2), new ApronInterval(-3, 5), new ApronInterval(0.75, 1.2)), ApronDom.Box)
    println(dom + "\n\n\n")

    println("Bound of x2: " + dom.getBound(2).interval + "\n")

    val ltrms = Array(new ApronLinTerm(-5, 1), new ApronLinTerm(0.1, 0.6, 0), new ApronLinTerm(0.1, 2))
    println(ltrms.foldLeft("Linear terms:\n")((acc, t) => acc + "  " + t + "\n"))
    val linexpr = new ApronLinExpr(2, ltrms)
    println("Linear expression: " + linexpr + "\n")
    println("Bound of linear expression: " + dom.getBound(linexpr).interval + "\n")
    val lincons = new ApronLinCons(linexpr, LE)
    println("Linear constraint: " + lincons + "\n")
    println("If the given domain satisfies the linear constraint: " + dom.satisfy(lincons))

    println("\n\n")
    val txpr = new ApronNonLinTerm(ADD, new ApronNonLinTerm(MUL, 0, 1), new ApronNonLinTerm(DIV, 2, 2.0))
    println("Nonlinear term: " + txpr + "\n")
    val texpr = new ApronNonLinExpr(txpr)
    println("Nonlinear expression: " + texpr + "\n")
    println("Bound of nonlinear expression:  " + dom.getBound(texpr).interval + "\n")
    val tcons = new ApronNonLinCons(texpr, LE)
    println("Linear constraint: " + tcons + "\n")
    println("If the given domain satisfies the linear constraint: " + dom.satisfy(tcons))
  }

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
