import sbt.Keys.scalacOptions

name := "sbouken"
version := "0.1"

scalaVersion := "2.12.8"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.typelevel" %% "cats-mtl-core" % "0.4.0",
  // circe
  "io.circe" %% "circe-core" % "0.10.0",
  "io.circe" %% "circe-generic" % "0.10.0",
  "io.circe" %% "circe-parser" % "0.10.0",
  // mtl
  "com.olegpy" %% "meow-mtl" % "0.2.0",
  //enum
  "com.beachape" %% "enumeratum" % "1.5.13",
  "com.beachape" %% "enumeratum-circe" % "1.5.14",
  //other
  "com.github.mpilquist" %% "simulacrum" % "0.14.0",
  // test
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

scalacOptions ++=
  Seq(
    "-Ypartial-unification",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:experimental.macros"
  )

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

addCommandAlias("validate", ";coverage;test;coverageReport")
