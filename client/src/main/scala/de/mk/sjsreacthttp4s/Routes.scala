package de.mk.sjsreacthttp4s

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Resolution, Router, RouterConfigDsl, RouterCtl}
import japgolly.scalajs.react.vdom.html_<^._
import scalacss.ScalaCssReact._


object Routes {
  sealed trait Page
  case object Home extends Page
  case object About extends Page

  private val routerConfig = RouterConfigDsl[Page].buildConfig { dsl =>
    import dsl._

    (trimSlashes |
      staticRoute(root, Home) ~> render(<.h1("Home")) |
      staticRoute("#about", About) ~> render(<.h1("About")))
      .notFound(redirectToPage(Home)(Redirect.Replace))
      .renderWith(masterPage)
  }

  private val routes = Router.apply(BaseUrl.fromWindowOrigin, routerConfig)

  def apply(): Unmounted[Unit, Resolution[Page], OnUnmount.Backend] = routes()

  private def masterPage(ctl: RouterCtl[Page], r: Resolution[Page]) =
    <.div(
      Navigation(ctl, r),
      <.div(BootstrapCss.container, r.render())
    )

  object Navigation {

    private def navElement(c: RouterCtl[Page], page: Page, name: String, isActive: Boolean) =
      <.li(BootstrapCss.navItem, BootstrapCss.active.when(isActive),
        <.a(BootstrapCss.navLink, ^.onClick --> c.set(page), name)
      )

    private val component = ScalaComponent.builder[(RouterCtl[Page], Resolution[Page])]("Navigation")
      .render_P(prop =>
        <.nav(
          BootstrapCss.navBar,
          <.span(BootstrapCss.navBarBrand, "Template"),
          <.div(
            BootstrapCss.navBarCollapse,
            <.ul(
              BootstrapCss.navBarNav,
              navElement(prop._1, Home, "Home", prop._2.page == Home),
              navElement(prop._1, About, "About", prop._2.page == About)
            )
          )
        )
      ).build

    def apply(ctl: RouterCtl[Routes.Page], res: Resolution[Page]): Unmounted[(RouterCtl[Page], Resolution[Page]), Unit, Unit] =
      component((ctl, res))
  }

}
