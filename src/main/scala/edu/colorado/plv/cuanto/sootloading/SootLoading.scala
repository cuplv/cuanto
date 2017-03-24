package edu.colorado.plv.cuanto.sootloading


import java.util.concurrent.locks.ReentrantLock


import soot.options.Options
import soot._

import scala.collection.JavaConverters._

/**
  * Created by s on 3/17/17.
  */
object SootLoading {

  //Note: soot uses a lot of global static variables, we can only run one copy at a time and it must be reset
  val lock : ReentrantLock = new ReentrantLock()

  val fileSep: String = System.getProperty("file.separator")
  val pathSep: String = System.getProperty("path.separator")
  val javaLibraryPath: String = System.getProperty("java.home") + fileSep + "lib" + fileSep
  val jcePath: String = javaLibraryPath + "jce.jar"
  val rtPath: String = javaLibraryPath + "rt.jar"


  def getAnalysisResult[T](paths: List[String], main: Option[String] = None,analysis: Scene=>T): Option[T] ={
    lock.lock()
    Options.v().keep_line_number()
    Options.v().set_src_prec(Options.src_prec_class)
    Options.v().set_process_dir(paths.asJava)
    Options.v().unfriendly_mode() //allow to run with no command line args
    Options.v().set_allow_phantom_refs(true)
    Options.v().set_whole_program(true)
    main match{
      case Some(main) => Options.v().set_main_class(main)
      case None =>
    }

    val path: String = Scene.v().getSootClassPath
    Scene.v().setSootClassPath(path + ":" + paths.foldLeft("")((acc, str) => acc + str + pathSep) + jcePath + pathSep + rtPath)
    val getJimple: GetJimple[T] = new GetJimple(analysis)
    PackManager.v().getPack("wjtp").add(new Transform("wjtp.get_jimple", getJimple))
    soot.Main.main(Array("-unfriendly-mode"))
    lock.unlock()
    G.reset()
    getJimple.result

  }

}