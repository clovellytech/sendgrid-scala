import dependencies._

val withTests: String = "compile->compile;test->test"

cancelable in Global := true

val scala212 = "2.12.11"
val scala213 = "2.13.2"

inThisBuild(
  Seq(
    organization := "com.clovellytech",
    homepage := Some(url("https://github.com/clovellytech/sendgrid-scala")),
    licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT")),
    developers := List(
      Developer(
        "zakpatterson",
        "Zak Patterson",
        "pattersonzak@gmail.com",
        url("https://github.com/zakpatterson")
      )
    )
  )
)

val commonSettings = Seq(
  scalaVersion := scala213,
  crossScalaVersions := Seq(scala212, scala213),
  resolvers ++= addResolvers,
  scalacOptions ++= options.scalacExtraOptionsForVersion(scalaVersion.value),
  libraryDependencies ++= compilerPluginsForVersion(scalaVersion.value)
)

lazy val core = project
  .in(file("./modules/core"))
  .settings(commonSettings)

lazy val docs = project
  .in(file("./sendgrid-scala-docs"))
  .settings(
    crossScalaVersions := Seq(scala212),
    moduleName := "sendgrid-scala-docs",
    mdocVariables := Map(
      "VERSION" -> version.value
    ),
    cancelable in Global := true,
    skip in publish := true
  )
  .enablePlugins(MdocPlugin)
  .enablePlugins(DocusaurusPlugin)
  .dependsOn(core)

lazy val root = project
  .in(file("."))
  .settings(name := "sendgrid-scala-root")
  .settings(commonSettings)
  .settings(
    skip in publish := true
  )
  .dependsOn(core)
  .aggregate(core)
