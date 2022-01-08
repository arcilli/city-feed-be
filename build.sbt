import sbt.Keys.libraryDependencies

name := "city-feed-be"

version := "0.1"
enablePlugins(AkkaGrpcPlugin)

val akkaVersion = "2.6.9"
val akkaHttpVersion = "10.2.7"
val doobieVersion = "0.9.4"

lazy val root = Project("city-feed-be", file("."))
  .settings(
    IntegrationTest / fork := false,
    fork := true
  )
  .settings(
    //debugSettings in reStart := Some(DebugSettings(5005, suspend = false)),
    scalaVersion := "2.13.5",
    mainClass.withRank(KeyRanks.Invisible) := Some("cityfeed.Main"),
    IntegrationTest / parallelExecution := false,

    libraryDependencies ++= Seq(
      "org.tpolecat"               %% "doobie-core"               % doobieVersion,
      "org.tpolecat"               %% "doobie-h2"                 % doobieVersion,
      "org.tpolecat"               %% "doobie-hikari"             % doobieVersion,
      "org.tpolecat"               %% "doobie-postgres"           % doobieVersion,
      "org.tpolecat"               %% "doobie-postgres-circe"     % doobieVersion,
      "org.tpolecat"               %% "doobie-specs2"             % doobieVersion   % "test",
      "org.tpolecat"               %% "doobie-scalatest"          % doobieVersion   % "test",
      "org.scalatest"              %% "scalatest"                 % "3.2.9"         % "test",
      "com.github.pureconfig"      %% "pureconfig"                % "0.17.1",
      "org.typelevel"              %% "cats-effect"               % "2.4.1",
      "com.typesafe.scala-logging" %% "scala-logging"             % "3.9.3",
      "com.typesafe.akka"          %% "akka-actor"                % akkaVersion,
      "com.typesafe.akka"          %% "akka-actor-typed"          % akkaVersion,
      "com.typesafe.akka"          %% "akka-slf4j"                % akkaVersion,
      "com.typesafe.akka"          %% "akka-stream"               % akkaVersion,
      "com.typesafe.akka"          %% "akka-http"                 % akkaHttpVersion,
      "com.typesafe.akka"          %% "akka-discovery"            % akkaVersion,
      "com.typesafe.akka"          %% "akka-actor-testkit-typed"  % akkaVersion % Test,
      "nl.wehkamp.cakemix"         %% "cakemix"                   % "1.3.0",
      "io.circe"                   %% "circe-parser"              % "0.14.1",
      "io.circe"                   %% "circe-core"                % "0.14.1",
      "io.circe"                   %% "circe-generic"             % "0.14.1",
      "com.github.pureconfig"      %% "pureconfig"                % "0.17.1",
      "ch.megard"                  %% "akka-http-cors"            % "0.4.2",
      "org.flywaydb"                % "flyway-core"               % "6.2.1",
      "ch.qos.logback"              % "logback-classic"           % "1.1.2"
    )
  )

