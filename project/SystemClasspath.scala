import sbt._

object SystemClasspath {

  private[this] val systemClasspathRaw =
    sys.env.get("CLASSPATH") match {
      case Some(path) => path
      case _ => ""
    }

  private[this] val jars: Seq[File] =
    systemClasspathRaw.split(':').map(file).toSeq

  val systemClasspath = jars.classpath

}
