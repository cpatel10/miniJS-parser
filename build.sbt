name := "expressions-scala"

version := "0.2"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "org.parboiled" %% "parboiled" % "2.1.1",
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "jline" % "jline" % "2.12.1"
)
