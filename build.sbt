// The simplest possible sbt build file is just one line:

scalaVersion := "2.12.6"
// That is, to create a valid sbt build, all you've got to do is define the
// version of Scala you'd like your project to use.

// ============================================================================

// Lines like the above defining `scalaVersion` are called "settings" Settings
// are key/value pairs. In the case of `scalaVersion`, the key is "scalaVersion"
// and the value is "2.12.6"

// It's possible to define many kinds of settings, such as:

name := "Memcache"
organization := "me.ghiasi"
version := "1.0"

// Note, it's not required for you to define these three settings. These are
// mostly only necessary if you intend to publish your library's binaries on a
// place like Sonatype or Bintray.


// Want to use a published library in your project?
// You can define other libraries as dependencies in your build like this:
libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.3" % Test
)
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.14",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.14" % Test
)
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.14",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.14" % Test
)
libraryDependencies +=
  "com.typesafe.akka" %% "akka-cluster-sharding" %  "2.5.14"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-cluster" % "2.5.14"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-distributed-data" % "2.5.14"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-persistence" % "2.5.14"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
)
// Scala 2.10, 2.11, 2.12
libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"       % "3.2.2",
  "com.h2database"  %  "h2"                % "1.4.197",
  "ch.qos.logback"  %  "logback-classic"   % "1.2.3"
)

libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.3"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.4"

val circeVersion = "0.9.3"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "com.typesafe.akka" %% "akka-cluster-sharding" % "2.5.14"
libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.5.14"

libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

libraryDependencies ++= Seq(
  "com.okumin" %% "akka-persistence-sql-async" % "0.5.1",
  "com.github.mauricio" %% "mysql-async" % "0.2.21",
)

libraryDependencies += "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.87"

//libraryDependencies += Seq(
//  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.87",
//  "com.typesafe.akka" %% "akka-persistence-cassandra-launcher" % "0.87"
//)
//resolvers += "krasserm at bintray" at "http://dl.bintray.com/krasserm/maven"
//
//libraryDependencies += "com.github.krasserm" %% "akka-persistence-cassandra-3x" % "0.6"
//libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % "2.5.14"

// Here, `libraryDependencies` is a set of dependencies, and by using `+=`,
// we're adding the cats dependency to the set of dependencies that sbt will go
// and fetch when it starts up.
// Now, in any Scala file, you can import classes, objects, etc, from cats with
// a regular import.

// TIP: To find the "dependency" that you need to add to the
// `libraryDependencies` set, which in the above example looks like this:

// "org.typelevel" %% "cats-core" % "1.1.0"

// You can use Scaladex, an index of all known published Scala libraries. There,
// after you find the library you want, you can just copy/paste the dependency
// information that you need into your build file. For example, on the
// typelevel/cats Scaladex page,
// https://index.scala-lang.org/typelevel/cats, you can copy/paste the sbt
// dependency from the sbt box on the right-hand side of the screen.

// IMPORTANT NOTE: while build files look _kind of_ like regular Scala, it's
// important to note that syntax in *.sbt files doesn't always behave like
// regular Scala. For example, notice in this build file that it's not required
// to put our settings into an enclosing object or class. Always remember that
// sbt is a bit different, semantically, than vanilla Scala.

// ============================================================================

// Most moderately interesting Scala projects don't make use of the very simple
// build file style (called "bare style") used in this build.sbt file. Most
// intermediate Scala projects make use of so-called "multi-project" builds. A
// multi-project build makes it possible to have different folders which sbt can
// be configured differently for. That is, you may wish to have different
// dependencies or different testing frameworks defined for different parts of
// your codebase. Multi-project builds make this possible.

// Here's a quick glimpse of what a multi-project build looks like for this
// build, with only one "subproject" defined, called `root`:

// lazy val root = (project in file(".")).
//   settings(
//     inThisBuild(List(
//       organization := "ch.epfl.scala",
//       scalaVersion := "2.12.6"
//     )),
//     name := "hello-world"
//   )

// To learn more about multi-project builds, head over to the official sbt
// documentation at http://www.scala-sbt.org/documentation.html
