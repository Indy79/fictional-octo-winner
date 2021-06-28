ThisBuild / scalaVersion := "2.13.6"
ThisBuild / organization := "com.example"
ThisBuild / dynverSeparator := "-"

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.7"
val AkkaVersion = "2.6.14"
val AkkaHttpVersion = "10.2.4"
val AkkaManagementVersion = "1.1.0"

Compile / mainClass := Some("com.example.HttpServerRoutingMinimal")

lazy val hello = (project in file("."))
  .aggregate(helloRest)
  .dependsOn(helloRest)
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(
    name := "Example",
    libraryDependencies += scalaTest % Test
  )

lazy val helloCore = (project in file("core"))
  .settings(
    name := "Hello Core",
    libraryDependencies += scalaTest % Test
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
      "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
      "com.typesafe.akka" %% "akka-cluster" % AkkaVersion,
      "com.typesafe.akka" %% "akka-cluster-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-cluster-sharding" % AkkaVersion,
      "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
      "com.lightbend.akka.management" %% "akka-management" % AkkaManagementVersion,
      "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % AkkaManagementVersion,
      "com.lightbend.akka.management" %% "akka-management-cluster-http" % AkkaManagementVersion
    )
  )

dockerBaseImage := "adoptopenjdk:11-jre-hotspot"
