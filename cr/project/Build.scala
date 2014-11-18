import sbt._
import sbt.Keys._
import play.Play.autoImport._


object ApplicationBuild extends Build {

  val appName         = "cr"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.specs2"          %%  "specs2"        % "2.3.13"   % "test",
    "org.mockito"         %   "mockito-all"   % "1.9.5"   % "test",
    "com.rabbitmq"        %   "amqp-client"   % "3.1.1",
    "me.moocar"           %   "logback-gelf"  % "0.9.6p2",
    "postgresql"          %   "postgresql"    % "9.1-901.jdbc4",
    "com.dwp.carers"      %% "carerscommon"   % "6.1"
  )

  var sO:Setting[_] = scalacOptions := Seq("-deprecation", "-unchecked", "-encoding", "utf8","-feature")

  var sV: Seq[Def.Setting[_]] = Seq(scalaVersion := "2.10.4")

  var sR:Seq[Setting[_]] = Seq(
    resolvers += "Carers repo" at "http://build.3cbeta.co.uk:8080/artifactory/repo/",
    resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases")

  var jO: Seq[Def.Setting[_]] = Seq(testOptions in Test += Tests.Argument("sequential", "true"),
    javaOptions in Test += "-Doverride.rabbit.uri="+(System.getProperty("override.rabbit.uri") match { case s:String => s case null => ""}))

  var f: Seq[Def.Setting[_]] = Seq(sbt.Keys.fork in Test := false)

  var sOrg:Seq[Def.Setting[_]] = Seq(organization  := "uk.gov.service.carersallowance")

  var vS: Seq[Def.Setting[_]] = Seq(version := appVersion, libraryDependencies ++= appDependencies)

  var appSettings: Seq[Def.Setting[_]] =  sV ++ sO ++ sR  ++ vS ++ jO ++sOrg ++ f

  val main = Project(appName, file(".")).enablePlugins(play.PlayScala).settings(appSettings: _*)

}
