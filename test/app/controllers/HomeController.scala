package controllers

import javax.inject._


import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.data._
import play.api.data.Forms._
import ejisan.play.libs.{ PageMetaApi, PageMetaSupport }

/**
  * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets
) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.partials.menu())
  }

  def todo() = Action {
    Ok(views.html.basics.todo())
  }

  def todoApp() = Action {
    Ok(views.html.basics.todoapp())
  }

}
