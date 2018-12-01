package de.mk.sjsreacthttp4s

import cats.effect.IO
import fs2.{Stream, StreamApp}
import fs2.StreamApp.ExitCode
import org.http4s.server.blaze._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.staticcontent.WebjarService
import org.http4s.server.staticcontent.WebjarService.Config

import scala.concurrent.ExecutionContext.Implicits.global

object Server extends StreamApp[IO]{

  val webJarService: HttpService[IO] = WebjarService(Config[IO]())

  val indexHtmlService: HttpService[IO] = HttpService[IO] {
    case GET -> Root =>
      Ok(s"""
            |<!DOCTYPE html>
            |<html>
            |  <head>
            |    <meta charset="UTF-8">
            |    <script type="text/javascript" src="${de.mk.BuildInfo.assetsPath}/client-jsdeps.js"></script>
            |  </head>
            |  <body>
            |   <div id="app"></div>
            |   <script type="text/javascript" src="${de.mk.BuildInfo.assetsPath}/client-fastopt.js"></script>
            |  </body>
            |</html>
    """.stripMargin.trim).map(_.withType(MediaType.`text/html`))
  }

  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] =
    BlazeBuilder[IO]
      .bindHttp(8080, "localhost")
      .mountService(webJarService, s"/${de.mk.BuildInfo.assetsRoot}")
      .mountService(indexHtmlService, "/")
      .serve

}
