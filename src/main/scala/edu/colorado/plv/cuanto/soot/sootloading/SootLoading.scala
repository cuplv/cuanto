package edu.colorado.plv.cuanto.scoot.sootloading

import java.util.concurrent.locks.ReentrantLock

import soot.options.Options
import soot._

import scala.collection.JavaConverters._
import scala.util.Try

/**
  * Created by s on 3/17/17.
  * Use this class by implementing a lambda which transforms a soot Scene into a
  * type T which is the analysis result. Call getAnalysisResult with this lambda.
  *
  * Returns: Try which either contains the result or an exception
  *
  * Caviats: Due to the static mutable fields in Soot only one analysis can be running at a time in a program.  This
  * Analysis implemets locks so if results are requested concurrently it will simply block until things are ready.
  * Avoid deadlocks by not making concurrent analyses depend on eachother's results.
  */
object SootLoading {

  //Note: soot uses a lot of global static variables, we can only run one copy at a time and it must be reset
  val lock : ReentrantLock = new ReentrantLock()

  val fileSep: String = System.getProperty("file.separator")
  val pathSep: String = System.getProperty("path.separator")
  val javaLibraryPath: String = System.getProperty("java.home") + fileSep + "lib" + fileSep
  val jcePath: String = javaLibraryPath + "jce.jar"
  val rtPath: String = javaLibraryPath + "rt.jar"


  /**
    *
    * @param paths list of paths to all java classes to consider
    * @param main select a main method to start analysis from,
    *             will choose a single entry point automatically if not provided
    * @param analysis Lambda which takes the soot Scene and creates a result (see Soot documentation for scene info)
    * @tparam T Type of the result returned by the analysis
    * @return a try with the analysis result if there was no error or the error.
    */
  def getAnalysisResult[T](paths: List[String], main: Option[String] = None,analysis: Scene=>T): Try[T] ={
    try {
      lock.lock() //Lock used to prevent concurrent use of global static soot variables
      Try({
        Options.v().keep_line_number() //Soot does not automatically retain line number info, this option keeps it
        Options.v().set_src_prec(Options.src_prec_class) //Type of files in source to retain
        Options.v().set_process_dir(paths.asJava) //set the list of java paths to consider
        Options.v().unfriendly_mode() //allow to run with no command line args
        //TODO: figure out best way to deal with this
        Options.v().set_allow_phantom_refs(true) //Create fake versions of classes which cannot be resolved (DANGER!)
        Options.v().set_whole_program(true) //Option needed to run whole jimple program transformations
        main foreach { main => Options.v().set_main_class(main) }  //Set single entry point
        val path: String = Scene.v().getSootClassPath
        //Combine paths into semi colon separated list
        Scene.v().setSootClassPath(path + ":" + paths.foldLeft("")((acc, str) =>
          acc + str + pathSep) + jcePath + pathSep + rtPath)
        val getJimple: GetJimple[T] = new GetJimple(analysis)
        PackManager.v().getPack("wjtp").add(new Transform("wjtp.get_jimple", getJimple))
        soot.Main.main(Array("-unfriendly-mode"))
        getJimple.result match{
          case Some(result) => result
          case None => throw new RuntimeException("Analysis failed")
        }
      }
      )
    }finally {
      G.reset()
      lock.unlock()
    }



  }

}
