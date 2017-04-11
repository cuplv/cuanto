package edu.colorado.plv.cuanto.sootloading

import java.util

import soot.options.Options
import soot._

import scala.collection.JavaConverters._

/**
  * Created by s on 3/17/17.
  */
object SootLoading {
    val fileSep: String = System.getProperty("file.separator")
    val pathSep: String = System.getProperty("path.separator")
    val javaLibraryPath: String = System.getProperty("java.home") + fileSep + "lib" + fileSep
    val jcePath: String = javaLibraryPath + "jce.jar"
    val rtPath: String = javaLibraryPath + "rt.jar"

    def getAnalysisResult[T](paths: List[String], main: Option[String] = None, analysis: Scene => T): Option[T] = {
        G.reset()

        Options.v().keep_line_number()
        Options.v().set_src_prec(Options.src_prec_class)
        Options.v().set_process_dir(paths.asJava)
        Options.v().unfriendly_mode() //allow to run with no command line args
        Options.v().set_allow_phantom_refs(true)
        Options.v().set_whole_program(true)
        main match {
            case Some(_main) => Options.v().set_main_class(_main)
            case None =>
        }

        // Set soot class path to directories: (1) where the jars to be analyzed are located (2) rt.jar (3) jce.jar
        Scene.v().setSootClassPath(paths.foldLeft("")((acc, str) => acc + str) + pathSep + jcePath + pathSep + rtPath)
        val getJimple: GetJimple[T] = new GetJimple(analysis)
        PackManager.v().getPack("wjtp").add(new Transform("wjtp.get_jimple", getJimple))
        soot.Main.main(Array("-unfriendly-mode"))
        getJimple.result
    }

}
