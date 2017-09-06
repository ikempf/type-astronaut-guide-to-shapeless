import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.ikempf",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "type-astronaut-guide-to-shapeless",
    libraryDependencies ++= Seq(
      cats,
      shapeless,
      scalaTest % Test
    )
  )
