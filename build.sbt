import sbt.Keys.scalacOptions

name := "sbouken"
version := "0.1"

scalaVersion in ThisBuild := "2.12.7"

lazy val root = project.in(file(".")).
  aggregate(sboukenJs, sboukenJVM).
  settings(
    publish := {},
    publishLocal := {}
)

lazy val xproject = crossProject.in(file(".")).
  settings(
    name := "sbouken",
    version := "0.1-SNAPSHOT",
    scalacOptions += "-Ypartial-unification"
  ).
  jvmSettings(
    // Add JVM-specific settings here
    libraryDependencies ++= Seq(
      "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
      "org.typelevel" %% "cats-core" % "1.4.0",
      "org.scalatest" %%% "scalatest" % "3.0.5" % "test"
    )
  ).
  jsSettings(
    // Add JS-specific settings here
  )

lazy val sboukenJVM = xproject.jvm
lazy val sboukenJs = xproject.js

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

addCommandAlias("validate", ";coverage;test;coverageReport")
