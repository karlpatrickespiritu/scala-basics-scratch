name := """test"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.webjars" %% "webjars-play" % "2.5.0-2",
  "org.webjars" % "riot" % "2.2.4",
  "com.ejisan" %% "play-pagemeta" % "1.2.1",
  "com.ejisan" %% "play-form" % "2.0.2",
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
  "org.postgresql" % "postgresql" % "9.4.1208"
)

// Web Jars Dependencies
libraryDependencies ++= Seq(
  "org.webjars" % "webjars-play_2.11" % "2.5.0",
  "org.webjars" % "jquery" % "2.2.2",
  "org.webjars" % "foundation" % "6.2.0"
)

// Resolvers
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "Ejisan Github" at "https://ejisan.github.io/repo/"

// Twirl importing classes
TwirlKeys.templateImports += "ejisan.play.libs.PageMeta"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

// Scala compiler options
scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature",
  "-optimise",
  "-explaintypes",
  "-encoding",
  "UTF-8",
  "-Xlint"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
