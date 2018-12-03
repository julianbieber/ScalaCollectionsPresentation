name := "CollectionsBenchmark2.11"

version := "0.1"

scalaVersion := "2.11.12"


lazy val core = project.in(file("core"))

lazy val root = project.in(file("."))
  .aggregate(`core`)
