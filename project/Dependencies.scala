import sbt._, Keys._

object dependencies {
  val addResolvers = Seq(
    Resolver.sonatypeRepo("public")
  )

  val compilerPlugins = Seq(
    compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
  )

  def compilerPluginsForVersion(version: String): Seq[librarymanagement.ModuleID] =
    CrossVersion.partialVersion(version) match {
      case Some((2, major)) if major < 13 =>
        compilerPlugins ++ Seq(
          compilerPlugin(
            ("org.scalamacros" % "paradise" % versions.macroParadise).cross(CrossVersion.full)
          ),
          compilerPlugin("org.typelevel" %% "kind-projector" % versions.kindProjector212)
        )
      case Some((2, major)) if major == 13 =>
        compilerPlugins ++ Seq(
          compilerPlugin(
            "org.typelevel" % s"kind-projector_$version" % versions.kindProjector213
          )
        )
      case _ => compilerPlugins
    }

  object versions {
    val cats = "2.1.0"
    val catsMtl = "0.7.0"
    val catsEffect = "2.0.0"
    val circe = "0.13.0"
    val circeFs2 = "0.12.0"
    val circeConfig = "0.7.0"
    val http4s = "0.21.4"
    val kindProjector212 = "0.10.3"
    val kindProjector213 = "0.11.0"
    val logback = "1.2.3"
    val macroParadise = "2.1.1"
    val scalaCheck = "1.14.3"
    val scalatags = "0.8.2"
    val scalatest = "3.2.0-M1"
    val simulacrum = "1.0.0"
  }

}
