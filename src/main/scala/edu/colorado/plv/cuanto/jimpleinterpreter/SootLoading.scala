package edu.colorado.plv.cuanto.jimpleinterpreter

import soot._
import soot.options.Options

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * Created by s on 3/17/17.
  */
object SootLoading {
    val fileSep: String = System.getProperty("file.separator")
    val pathSep: String = System.getProperty("path.separator")
    val javaLibraryPath: String = System.getProperty("java.home") + fileSep + "lib" + fileSep
    val jcePath: String = javaLibraryPath + "jce.jar"
    val rtPath: String = javaLibraryPath + "rt.jar"

    def init(paths: List[String], mainClass: Option[String] = None, mainMethod: Option[String] = None) = {
        G.reset()

        Options.v().keep_line_number()
        Options.v().set_src_prec(Options.src_prec_class)
        Options.v().set_process_dir(paths.asJava)
        Options.v().unfriendly_mode() //allow to run with no command line args
        Options.v().set_allow_phantom_refs(true)
        Options.v().set_whole_program(true)

        // Set main class
        mainClass match {
            case Some(klass) => Options.v().set_main_class(klass)
            case None =>
        }

        // Set soot class path to directories: (1) where the jars to be analyzed are located (2) rt.jar (3) jce.jar
        Scene.v().setSootClassPath(paths.foldLeft("")((acc, str) => acc + str + pathSep) + jcePath + pathSep + rtPath)

        val jimpleInt = new JimpleInterpreter()
        PackManager.v().getPack("wjtp").add(new Transform("wjtp.jimple_interpreter", jimpleInt))

        soot.Main.main(Array("-unfriendly-mode"))

        G.reset()
    }
}
