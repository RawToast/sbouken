name := "sbouken"

version := "0.1"

scalaVersion := "2.12.7"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.4.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

scalacOptions += "-Ypartial-unification"

addCommandAlias("validate", ";coverage;test;coverageReport")

// shadow sbt-scalajs' crossProject and CrossType from Scala.js 0.6.x
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val sharedSettings = Seq(scalaVersion := "2.11.12")

lazy val bar =
// select supported platforms
  crossProject(JSPlatform, JVMPlatform, NativePlatform)
    .crossType(CrossType.Pure) // [Pure, Full, Dummy], default: CrossType.Full
    .settings(sharedSettings)
    .jsSettings(/* ... */) // defined in sbt-scalajs-crossproject
    .jvmSettings(/* ... */)
    // configure Scala-Native settings
    .nativeSettings(/* ... */) // defined in sbt-scala-native

// Optional in sbt 1.x (mandatory in sbt 0.13.x)
lazy val barJS     = bar.js
lazy val barJVM    = bar.jvm
lazy val barNative = bar.native

lazy val foo =
  crossProject(JSPlatform, JVMPlatform, NativePlatform)
    .settings(sharedSettings)
    .settings(
      // %%% now include Scala Native. It applies to all selected platforms
      libraryDependencies += "org.example" %%% "foo" % "1.2.3"
    )

// Optional in sbt 1.x (mandatory in sbt 0.13.x)
lazy val fooJS = foo.js
lazy val fooJVM = foo.jvm
lazy val fooNative = foo.native