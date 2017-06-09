package edu.colorado.plv.cuanto.soot.sootloading

import java.io.File

import soot.options.Options
import soot.shimple.ShimpleTransformer
import soot.{PackManager, Scene}
import scala.collection.JavaConverters._

import scala.util.Try

/**
  * Created by s on 6/9/17.
  */
object GetSootIR {
  def initSootTest(paths: List[String]): Scene = {

    soot.G.reset()
    Options.v().keep_line_number() //Soot does not automatically retain line number info, this option keeps it
    Options.v().set_src_prec(Options.src_prec_class) //Type of files in source to retain
    Options.v().set_process_dir(paths.asJava) //set the list of java paths to consider
    Options.v().unfriendly_mode() //allow to run with no command line args
    //TODO: figure out best way to deal with this
    Options.v().set_allow_phantom_refs(true) //Create fake versions of classes which cannot be resolved (DANGER!)
    Options.v().set_whole_program(true) //Option needed to run whole jimple program transformations
    val scene = soot.Scene.v()
    scene.loadBasicClasses()
    if(paths.length < 1){
      throw new IllegalArgumentException("At least one path needed")
    }
    val resourceURL = paths(0)
    val sootTestDir = new File(resourceURL)
    scene.setSootClassPath(sootTestDir.toString())

//    PackManager.v().runPacks()
//    ShimpleTransformer.v.transform()
    scene.loadDynamicClasses()
    PackManager.v().getPack("wjpp").apply();
    PackManager.v().getPack("cg").apply();
//    PackManager.v().getPack("wjtp").apply();
//    PackManager.v().getPack("wjop").apply();
//    PackManager.v().getPack("wjap").apply();

    scene
  }

  def getAnalysisResult[T](paths: List[String], main: Option[String] = None) ={

  }

}
