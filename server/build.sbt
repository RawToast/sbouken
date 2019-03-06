import sbt.Keys.scalacOptions

name := "sbouken"
version := "0.1"

scalaVersion := "2.12.8"

val circeVersion = "0.11.1"
val http4sVersion = "0.20.0-M5"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.typelevel" %% "cats-mtl-core" % "0.4.0",
  // circe
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-generic-extras" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  // https
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  // mtl
  "com.olegpy" %% "meow-mtl" % "0.2.0",
  //enum
  "com.beachape" %% "enumeratum" % "1.5.13",
  "com.beachape" %% "enumeratum-circe" % "1.5.14",
  //other
  "com.github.mpilquist" %% "simulacrum" % "0.14.0",
  "org.typelevel" %% "mouse" % "0.20",
  "com.softwaremill.quicklens" %% "quicklens" % "1.4.11",
  // test
  "io.circe" %% "circe-literal" % circeVersion  % "test",
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
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0-M4")

addCommandAlias("validate", ";coverage;test;coverageReport")
