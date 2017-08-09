import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"
  
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.4"

  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  
  lazy val scalaParserCombinators = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.5"
  
  lazy val sootResolvers = Seq(
    "soot snapshot" at "https://soot-build.cs.uni-paderborn.de/nexus/repository/soot-snapshot/",
    "soot release" at "https://soot-build.cs.uni-paderborn.de/nexus/repository/soot-release/"
  )
  lazy val soot = "ca.mcgill.sable" % "soot" % "3.0.0-SNAPSHOT"

  lazy val walaCore = Seq(
    "com.ibm.wala" % "com.ibm.wala.util" % "1.4.2",
    "com.ibm.wala" % "com.ibm.wala.core" % "1.4.2"
  )

  lazy val sonatypeResolver = "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"
  lazy val scalaSMTLIB = "com.regblanc" %% "scala-smtlib" % "0.2.1"
}
