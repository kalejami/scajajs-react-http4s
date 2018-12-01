package de.mk.sjsreacthttp4s

import org.scalajs.dom.document

object Client {

  def main(args: Array[String]): Unit =
    Routes().renderIntoDOM(document.getElementById("app"))

}
