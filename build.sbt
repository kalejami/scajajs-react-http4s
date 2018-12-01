val http4sVersion = "0.18.11"

lazy val root = project.in(file(".")).aggregate(client).aggregate(server)

lazy val server = project.in(file("server"))
  .settings(
    scalaVersion := "2.12.7",

    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    ),

    scalacOptions ++= Seq(
      "-Ypartial-unification"
    ),

    scalaJSProjects := Seq(client),

    pipelineStages in Assets := Seq(scalaJSPipeline),

    buildInfoKeys := {
      val assetsRoot = "assetsRoot" -> "assets"
      val assetsPath = "assetsPath" -> s"${assetsRoot._2}/${moduleName.value}/${version.value}"
      Seq(assetsRoot, assetsPath)
    },

    buildInfoPackage := "de.mk"
).enablePlugins(SbtWeb, BuildInfoPlugin)

lazy val client =
  project
    .in(file("client"))
    .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
    .settings(
      scalaVersion := "2.12.6", //2.12.7 breaks with react-router!!

      libraryDependencies ++= Seq(
        "com.github.japgolly.scalajs-react" %%% "core" % "1.3.1"
      ),

      jsDependencies ++= Seq(
        "org.webjars.npm" % "react" % "16.6.3"
          /        "umd/react.development.js"
          minified "umd/react.production.min.js"
          commonJSName "React",

        "org.webjars.npm" % "react-dom" % "16.6.3"
          /         "umd/react-dom.development.js"
          minified  "umd/react-dom.production.min.js"
          dependsOn "umd/react.development.js"
          commonJSName "ReactDOM"
      ),

      dependencyOverrides ++= Seq(
        "org.webjars.npm" % "js-tokens" % "4.0.0",
        "org.webjars.npm" % "scheduler" % "0.11.0"
      ),

      scalaJSUseMainModuleInitializer := true
    )