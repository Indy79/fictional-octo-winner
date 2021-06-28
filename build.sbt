ThisBuild / scalaVersion := "2.13.6"
ThisBuild / organization := "com.example"

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.7"
val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.4"

Compile / mainClass := Some("com.example.HttpServerRoutingMinimal")

lazy val hello = (project in file("."))
  .aggregate(helloRest)
  .dependsOn(helloRest)
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(
    name := "Example",
    libraryDependencies += scalaTest % Test,
  )

lazy val helloCore = (project in file("core"))
  .settings(
    name := "Hello Core",
    libraryDependencies += scalaTest % Test,
  )

lazy val helloRest = (project in file("rest"))
  .aggregate(helloCore)
  .dependsOn(helloCore)
  .settings(
    name := "Hello Rest",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion
    ),
  )

dockerBaseImage := "adoptopenjdk:11-jre-hotspot"

