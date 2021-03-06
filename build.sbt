import Dependencies._
import SystemClasspath._

enablePlugins(SiteScaladocPlugin)
enablePlugins(GhpagesPlugin)

val cuantoClasspath = systemClasspath("CUANTO_CLASSPATH")

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      // Scalac
      scalacOptions ++= Seq(
        "-language:implicitConversions",
        "-encoding", "UTF-8", // check encoding
        "-feature", // warn on features that should be imported explicitly
        "-explaintypes", // explain type errors in more detail
        "-deprecation", // warn on deprecated APIs
        "-unchecked", // detailed erasure warnings
        "-Xfuture", // future language features
        //"-Xfatal-warnings", // fail on warnings
        //"-Xlint",
        //"-Ywarn-adapted-args", // warn if an argument list is modified to match the receiver
        //"-Ywarn-numeric-widen", // warn when numerics are widened
        "-Ywarn-inaccessible", // warn about inaccessible types in method signatures
        "-Ywarn-infer-any", // warn when a type argument is inferred to be `Any`
        "-Ywarn-nullary-override", // warn when non-nullary `def f()' overrides nullary `def f'
        "-Ywarn-nullary-unit", // warn when nullary methods return Unit
        "-Ywarn-unused", // warn when local and private vals, vars, defs, and types are unused
        "-Ywarn-unused-import", // warn when imports are unused
        "-Ywarn-value-discard" // warn when non-Unit expression results are unused
      ),

      // Scaladoc
      autoAPIMappings := true,

      organization := "edu.colorado.plv",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),

    // Scaladoc
    scalacOptions in (Compile, doc) ++= Seq(
      "-groups", // Group similar functions together (based on the @group annotation)
      "-implicits" // Document members inherited by implicit conversions.
    ),

    // Puts Scaladoc output in `target/site/api/latest`
    siteSubdirName in SiteScaladoc := "api/latest",
    
    // Publishing via sbt-site, sbt-ghpages, and Travis
    scmInfo := Some(ScmInfo(url("https://github.com/cuplv/cuanto"), "git@github.com:cuplv/cuanto.git")),
    git.remoteRepo := scmInfo.value.get.connection,

    // Dependencies
    libraryDependencies ++= Seq(
      scalaLogging,
      scalaParserCombinators,
      scalaTest % Test,
      scalaCheck % Test
    ),

    // Soot dependency using Paderborn Nexus
    resolvers ++= sootResolvers,
    libraryDependencies += soot,
    
    // Alternative Soot dependency using direct nightly build jar
    // libraryDependencies += soot from "https://soot-build.cs.uni-paderborn.de/nightly/soot/soot-trunk.jar",

    // Wala dependency
    libraryDependencies ++= walaCore,

    // scala-smtlib comes from the "sonatype releases" repository
    resolvers += sonatypeResolver,
    libraryDependencies += scalaSMTLIB,

    // Tell sbt to look at $CUANTO_CLASSPATH for unmanaged jars.  This
    // is where system-installed jars (such as jars that depend on
    // native libraries) are placed by the nix build.
    unmanagedClasspath in Compile ++= cuantoClasspath,
    unmanagedClasspath in Test ++= cuantoClasspath,

    // Name
    name := "cuanto"
  )

// Define an alias to run the test suite avoiding the slow tests
addCommandAlias("testFast","testOnly -- -l org.scalatest.tags.Slow")
