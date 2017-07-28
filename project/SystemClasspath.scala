import sbt._

object SystemClasspath {

  private[this] val systemClasspathRaw = (pathName: String) =>
    sys.env.get(pathName) match {
      case Some(path) => path
      case _ => ""
    }

  private[this] val jars: String => Seq[File] = pathName =>
    systemClasspathRaw(pathName).split(':').map(file).toSeq

  val systemClasspath = (pathName: String) => jars(pathName).classpath

}
