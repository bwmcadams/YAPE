import ScalateKeys._

seq(scalateSettings:_*)

name := "YAPE"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.5"

libraryDependencies += "org.pegdown" % "pegdown" % "1.4.2"

libraryDependencies += "org.parboiled" % "parboiled-scala_2.10" % "1.1.5"

libraryDependencies += "org.scalatra.scalate" %% "scalate-core" % "1.7.0"

// Scalate Precompilation and Bindings
scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
  Seq(
    TemplateConfig(
      base / "resources" / "templates",
      Seq.empty,
      Seq.empty,
      Some("templates")
    )
  )
}
