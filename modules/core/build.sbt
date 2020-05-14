import dependencies._

name := "sendgrid-core"

libraryDependencies ++= Seq(
  "http4s-blaze-client",
  "http4s-circe",
  "http4s-dsl"
).map("org.http4s" %% _ % versions.http4s) ++ Seq(
  "circe-core",
  "circe-generic",
  "circe-parser"
).map("io.circe" %% _ % versions.circe) ++ Seq(
  "io.circe" %% "circe-config" % versions.circeConfig,
  "io.circe" %% "circe-fs2" % versions.circeFs2,
  "org.typelevel" %% "simulacrum" % versions.simulacrum
) ++ Seq(
  "org.typelevel" %% "cats-core" % versions.cats,
  "org.typelevel" %% "cats-effect" % versions.catsEffect,
  "org.typelevel" %% "cats-mtl-core" % versions.catsMtl
) ++ Seq(
  "com.lihaoyi" %% "scalatags" % versions.scalatags,
)
