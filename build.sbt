name := """play-scala-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.196"

libraryDependencies ++= Seq(evolutions, jdbc)

//libraryDependencies ++= Seq(
//  "com.typesafe.play" %% "play-slick" % "3.0.0",
//  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0"
//)
