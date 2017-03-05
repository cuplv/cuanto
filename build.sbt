import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "edu.colorado.plv",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "cuanto",
    libraryDependencies ++= Seq(
      scalaParserCombinators,
      scalaTest % Test,
      scalaCheck % Test
    )
    libraryDependencies += scalaTest % Test
  )
