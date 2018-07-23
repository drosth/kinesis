import sbt.{Credentials, DefaultMavenRepository, Path, Resolver}

scalaVersion := "2.12.6"

val Revision = Option(System.getProperty("revision")).getOrElse("0")
val AkkaVersion = "2.5.12"
val AkkaHttpVersion = "10.1.1"
val AxonVersion = "3.2.2"

lazy val javaMemory = settingKey[String]("The amount of memory to give to the JVM")

lazy val `privacyservice` = (project in file("."))
    .settings(
      name := "kinesis-library",
      version := s"1.0.$Revision",
      organizationName := "de Persgroep Employment Solutions",
      organizationHomepage := Some(url("http://www.persgroepemploymentsolutions.nl")),
      organization := "nl.dpes",
      startYear := Some(2018),
      javaMemory := "384m",

      resolvers ++= Seq(
        DefaultMavenRepository,
        Resolver.typesafeRepo("releases"),
        Resolver.sonatypeRepo("public"),
        Resolver.sbtPluginRepo("releases"),
        "snapshots" at "https://archiva.persgroep.digital/repository/snapshots",
        "internal" at "https://archiva.persgroep.digital/repository/internal"
      ),
      credentials += Credentials(Path.userHome / ".archiva" / ".snapshots"),
      credentials += Credentials(Path.userHome / ".archiva" / ".internal"),

      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "3.2.0-SNAP9" % Test,
        "org.mockito" % "mockito-core" % "2.18.0" % Test,
        "org.hamcrest" % "hamcrest-core" % "1.3" % Test,
        "ch.qos.logback" % "logback-classic" % "1.2.3",

        "com.gilt" %% "gfc-aws-kinesis" % "0.17.0",
        "io.reactivex" %% "rxscala" % "0.26.5"
      ),

      javaOptions ++= Seq(
        "-Xmx" + javaMemory.value
      ),

      scalacOptions ++= Seq(
        "-feature",
        "-language:implicitConversions",
        "-language:postfixOps"
      ),
    )
