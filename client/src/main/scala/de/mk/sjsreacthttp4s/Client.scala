package de.mk.sjsreacthttp4s

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.document

object Client {
  private val component = ScalaComponent.builder[Unit]("Client").renderStatic(<.h1("Hallo Welt")).build

  def main(args: Array[String]): Unit =
    component().renderIntoDOM(document.getElementById("app"))

}
