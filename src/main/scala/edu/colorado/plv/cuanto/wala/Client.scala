package edu.colorado.plv.cuanto.wala

import com.typesafe.scalalogging.LazyLogging

import java.net.URL

import com.ibm.wala.classLoader.{IClass, IMethod}
import com.ibm.wala.ipa.callgraph.impl.Everywhere
import com.ibm.wala.ipa.callgraph.{AnalysisCache, AnalysisCacheImpl, AnalysisScope}
import com.ibm.wala.ipa.cha.{ClassHierarchyFactory, IClassHierarchy}
import com.ibm.wala.ssa.{IR, SSAOptions}
import com.ibm.wala.types.{MethodReference, Selector, TypeReference}
import com.ibm.wala.util.config.AnalysisScopeReader
import edu.colorado.plv.cuanto.wala.WalaConversions._

import scala.collection.JavaConverters._

/** Factory for [[Client]].
  *
  * @example {{{
  *              import WalaConversions.classURL
  *              val wala = Client(classOf[tests.MyTest])
  *          }}}
  * @author Bor-Yuh Evan Chang
  */
object Client extends LazyLogging {

  /** Alias for [[fromJavaBinary]] */
  def apply(classPath: URL): Client = fromJavaBinary(classPath)

  /** Creates a [[Client]] from a class path.
    *
    * @param classPath the class path
    */
  def fromJavaBinary(classPath: URL): Client = {
    require(classPath != null, s"Unable to find class path ${classPath}")
    val scope = makePrimordialScope
    val applicationLoader = scope.getApplicationLoader
    AnalysisScopeReader.addClassPathToScope(classPath.getPath, scope, applicationLoader)
    new Client(scope)
  }

  /** The [[Selector]] for a main method */
  lazy val MainSelector: Selector = {
    val stringArrayName = TypeReference.JavaLangString.getName.getArrayTypeForElementType
    val voidName = TypeReference.VoidName
    new Selector("main", Seq(stringArrayName) -> voidName)
  }

  private lazy val makePrimordialScope: AnalysisScope = {
    val PrimordialTxt = "wala/primordial.txt"
    val ClientClassLoader = getClass.getClassLoader
    val PrimordialTxtUrl: URL = {
      val u = ClientClassLoader.getResource(PrimordialTxt)
      assert(u != null, s"Unable to find primordial configuration ${PrimordialTxt}")
      logger.info(s"Using primordial configuration ${u}.")
      u
    }
    AnalysisScopeReader.readJavaScope(PrimordialTxtUrl.getPath, null, ClientClassLoader)
  }

}

/** Encapsulates the key WALA data structures.
  *
  * =Making IR=
  *
  * Encapsulates WALA's [[AnalysisCache]] to caches IRs and related information. Repeated calls to
  * the IR construction methods (e.g., [[makeMethodIR]]) will utilize this cache, so it is not
  * necessary to cache the IR externally.
  *
  * @param scope the WALA [[AnalysisScope]]
  * @author Bor-Yuh Evan Chang
  */
class Client(scope: AnalysisScope) {

  // Create an object which caches IRs and related information, reconstructing them lazily on demand.
  lazy private val cache: AnalysisCache = new AnalysisCacheImpl()

  /** The class hierarchy */
  lazy val classHierarchy: IClassHierarchy = ClassHierarchyFactory.make(scope)

  /** The application classes */
  lazy val applicationClasses: Set[IClass] =
    classHierarchy.asScala.foldLeft(Set.empty: Set[IClass]) { (set,c) =>
      if (scope.isApplicationLoader(c.getClassLoader)) set + c else set
    }

  /** Returns [[IR]] from a [[MethodReference]]
    *
    * @param m the method
    */
  def makeMethodIR(m: MethodReference): Option[IR] =
    Option(classHierarchy.resolveMethod(m)) flatMap makeMethodIR

  /** Returns [[IR]] from an [[IMethod]]
    *
    * @param m the method
    */
  def makeMethodIR(m: IMethod): Option[IR] =
    Option(cache.getSSACache.findOrCreateIR(m, Everywhere.EVERYWHERE, SSAOptions.defaultOptions))

  /** A map from methods to [[IR]] for those methods. */
  type IRMap = Map[MethodReference,IR]

  /** Returns an [[IRMap]] for each method of a given [[IClass]].
    *
    * @param c the class
    * @param process an optional callback to select methods to generate [[IR]]
    */
  def makeClassIR(c: IClass, process: IMethod => Boolean = { _ => true }): IRMap =
    makeClassIRT(c, process)(Map.empty)

  /** Returns an [[IRMap]] for methods matching a given [[Selector]].
    *
    * @param classes the classes
    * @param s the [[Selector]]
    */
  def makeIR(classes: Traversable[IClass], s: Selector): IRMap =
    makeIRT(classes, s)(Map.empty)

  /** Returns an [[IRMap]] transformer for each method of a given [[IClass]].
    *
    * @param process an optional callback to select methods to generate [[IR]]
    * @param c the class
    * @return the [[IRMap]] transformer adds the IR for the methods of c to the input [[IRMap]]
    */
  def makeClassIRT(c: IClass, process: IMethod => Boolean = { _ => true }): IRMap => IRMap = { irmap =>
    c.getDeclaredMethods.asScala.foldLeft(irmap) { (irmap, m) =>
      if (process(m))
        makeMethodIR(m) match {
          case None => irmap
          case Some(ir) => irmap + (m.getReference -> ir)
        }
      else irmap
    }
  }

  /** Returns an [[IRMap]] transformer for each method of a given collection of [[IClass]].
    *
    * @param classes the classes
    * @param process an optional callback to select methods to generate [[IR]]
    */
  def makeIRT(classes: Traversable[IClass], process: IMethod => Boolean = { _ => true }): IRMap => IRMap = {
    val mk = { c: IClass => makeClassIRT(c, process) }
    classes.foldLeft({ irmap: IRMap => irmap }) { (irmapt, c) => mk(c) compose irmapt }
  }

  /** Returns an [[IRMap]] for each method of a given collection of [[IClass]].
    *
    * @param classes the classes
    * @param process an optional callback to select methods to generate [[IR]]
    */
  def makeIR(classes: Traversable[IClass], process: IMethod => Boolean = { _ => true }): IRMap =
    makeIRT(classes, process)(Map.empty)

  /** Returns an [[IRMap]] transformer for methods matching a given [[Selector]].
    *
    * @param classes the classes
    * @param s the [[Selector]]
    */
  def makeIRT(classes: Traversable[IClass], s: Selector): IRMap => IRMap =
    makeIRT(classes, { m => m.getSelector == s })

}


