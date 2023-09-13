import Dependencies.scalaV
import sbt.Keys._
import sbt._



object ProjectSettings {

  lazy val settings = commonSettings ++ general

  lazy val compilerOptions = Seq(
    "-unchecked",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-deprecation",
    "-encoding",
    "utf8"
  )

   lazy val general = Seq(
    version := version.value,
    scalaVersion := scalaV,
    organization := "com.artemishealth",
    organizationName := "Artemishealth"
  )

  lazy val commonSettings = Seq(
    scalacOptions ++= compilerOptions,
    resolvers ++= Seq(
      "Artemis Maven Hosted Snapshots" at "https://nexus.internal.artemishealth.com/repository/maven-hosted-snapshots/",
      "Artemis Maven Proxy" at "https://nexus.internal.artemishealth.com/repository/artemis-maven-release-proxy/",
      Resolver.url("Artemis Ivy Proxy", url("https://nexus.internal.artemishealth.com/repository/artemis-ivy-release-proxy/"))(Resolver.ivyStylePatterns),
      "Artemis Maven Hosted" at "https://nexus.internal.artemishealth.com/repository/maven-hosted/",
      Resolver.defaultLocal,
      Resolver.mavenLocal,
      // Resolver.mavenLocal has issues - hence the duplication
      // the path separators in Windows are an issue with absolutePath so C:\Users\{user} has to change to /C:/Users/{user}
      Resolver.jcenterRepo,
      "Local Maven Repository" at "" + Path.userHome.asFile.toURI.toURL + ".m2/repository",
      Classpaths.typesafeReleases,
      Classpaths.sbtPluginReleases,
      // SHA-1 hash of the POM on maven central does not match, intellij requires it to match.  jboss's own repo works fine
      "JBoss" at "https://repository.jboss.org/",
      "Typesafe repository" at "https://repo.typesafe.com/typesafe/maven-releases/",
      "Flyaway" at "https://flywaydb.org/repo",
      "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
      "jitpack" at "https://jitpack.io",
      Resolver.bintrayRepo("tanukkii007", "maven")
    )
  )

}
