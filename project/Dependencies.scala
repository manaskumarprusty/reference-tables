import sbt._

object Dependencies {

  lazy val scalaV = "2.12.14"
  private lazy val sparkV = "3.2.1"
  private lazy val mockitoV = "1.10.19"
  private lazy val scalaTestV = "3.0.8"
  private lazy val scalaLoggingV   = "3.7.2"

  lazy val sparkCore = "org.apache.spark" %% "spark-core" % sparkV
  lazy val sparkSql = "org.apache.spark" %% "spark-sql" % sparkV
  lazy val scalatest = "org.scalatest" %% "scalatest" % scalaTestV % "test"
  lazy val mokitoCore = "org.mockito" % "mockito-core" % mockitoV % "test"
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingV

}