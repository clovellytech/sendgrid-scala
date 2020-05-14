import sbt._, Keys._

object dependencies {
  val addResolvers = Seq(
    Resolver.sonatypeRepo("public")
  )

  val compilerPlugins = Seq(
    addCompilerPlugin("org.typelevel" % "kind-projector_2.13.1" % "0.11.0"),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
  )

  object versions {
    val cats = "2.1.0"
    val catsMtl = "0.7.0"
    val catsEffect = "2.0.0"
    val circe = "0.13.0"
    val circeFs2 = "0.12.0"
    val circeConfig = "0.7.0"
    val http4s = "0.21.4"
    val logback = "1.2.3"
    val scalaCheck = "1.14.3"
    val scalatags = "0.8.2"
    val scalatest = "3.2.0-M1"
    val simulacrum = "1.0.0"
  }

}
