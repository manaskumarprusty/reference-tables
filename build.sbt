name := "reference-tables"
version := "1.0.0"


scalafmtOnCompile := true

lazy val global = project
  .in(file("."))
  .dependsOn(api)
  .settings(ProjectSettings.settings)
  .settings(libraryDependencies ++= commonDependencies)
  .disablePlugins(AssemblyPlugin)


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
