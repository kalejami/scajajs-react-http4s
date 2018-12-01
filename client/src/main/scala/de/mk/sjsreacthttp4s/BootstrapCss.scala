package de.mk.sjsreacthttp4s

import CssSettings._
import scalacss.internal.mutable.StyleSheet.Inline

object BootstrapCss extends Inline {
  import dsl._

  private def styleClasses(classes: String*) = style(addClassNames(classes: _*))

  val active: StyleA = styleClasses("active")
  val container: StyleA = styleClasses("container")

  val navBar: StyleA = styleClasses("navbar navbar-expand-sm navbar-light bg-light")
  val navBarBrand: StyleA = styleClasses("navbar-brand")
  val navBarCollapse: StyleA = styleClasses("collapse navbar-collapse")
  val navBarNav: StyleA = styleClasses("navbar-nav")

  val navLink: StyleA = styleClasses("nav-link")
  val navItem: StyleA = styleClasses("nav-link")
}
