package de.mk.sjsreacthttp4s

import org.scalajs.dom.document

import CssSettings._
import scalacss.ScalaCssReact._

object Client {

  def main(args: Array[String]): Unit = {
    BootstrapCss.addToDocument()
    Routes().renderIntoDOM(document.getElementById("app"))
  }

}
