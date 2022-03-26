scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.0-RC3",
  "dev.zio" %% "zio-streams" % "2.0.0-RC3",
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "org.slf4j" % "slf4j-simple" % "1.7.30",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test"
)
