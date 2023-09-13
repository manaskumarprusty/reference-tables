name := "reference-tables"
version := "1.0.0"


scalafmtOnCompile := true

lazy val global = project
  .in(file("."))
  .settings(ProjectSettings.settings)
  .disablePlugins(AssemblyPlugin)
  .aggregate(api)

lazy val api = project
  .settings(
    name := "api",
    ProjectSettings.settings,
    Assembly.settings,
    libraryDependencies ++= commonDependencies
  )


lazy val commonDependencies = Seq(Dependencies.sparkCore,
  Dependencies.sparkSql,
  Dependencies.scalaLogging,
  Dependencies.scalatest)
