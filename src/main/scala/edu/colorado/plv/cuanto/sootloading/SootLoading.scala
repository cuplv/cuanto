package edu.colorado.plv.cuanto.sootloading

import soot.options.Options
import soot._

import scala.collection.JavaConverters._

/**
  * Created by s on 3/17/17.
  */
object SootLoading {
  def getAnalysisResult[T](paths: List[String], main: Option[String] = None,analysis: Scene=>T): Option[T] ={
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

    val s: String = sys.env("JAVA_HOME") + "/jre/lib/rt.jar"
    println(s)
    Scene.v().setSootClassPath(path + ":" + s)
    val getJimple: GetJimple[T] = new GetJimple(analysis)
    PackManager.v().getPack("wjtp").add(new Transform("wjtp.get_jimple", getJimple))
    soot.Main.main(Array("-unfriendly-mode"))
    getJimple.result
  }

}
