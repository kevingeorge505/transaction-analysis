
lazy val root = (project in file("."))
  .settings(
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.11.8",
    organization := "com.se",
    name := "transaction-analysis",

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "2.4.3",
      "org.slf4j" % "slf4j-api" % "1.7.25",
      "org.scalatest" %% "scalatest" % "3.2.15" % "test"
    ),
    assembly / test := (Test / test).value,

    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs@_*) => MergeStrategy.discard
      case x => MergeStrategy.first
    }
  )



