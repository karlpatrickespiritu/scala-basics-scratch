package controllers

import java.lang.ProcessBuilder.Redirect
import javax.inject._

import play.api._
import play.api.mvc._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.data._
import play.api.data.Forms._
import ejisan.play.libs.{PageMetaApi, PageMetaSupport}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import models._
// import models.Users

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  val usersModel: tables.Users,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {

  var loginForm = Form {
    mapping(
      "id" -> optional(number),
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(User.apply) (User.unapply)
  }

  def index = Action.async { implicit request =>
    usersModel.list().map {
      users => Ok(views.html.index(loginForm, users))
    }
  }

  def deleteUser(id: Int) = Action.async { implicit request =>
    usersModel.delete(id).map {
      _ => Redirect(routes.HomeController.index)
    }
  }

  def showUpdate(id: Int) = Action.async { implicit request =>
    usersModel.findById(id).map {
      user => Ok(views.html.users.update(loginForm.fill(user.get)))
    }
  }

  def updateUser = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.users.update(formWithErrors)))
      },
      user => {
        usersModel.updateUser(user).map {
          _ => Redirect(routes.HomeController.index)
        }
      }
    )
  }

  def indexAction = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.index(formWithErrors)))
      },
      user => {
        usersModel.insert(user).map {
          _ => Redirect(routes.HomeController.index)
        }
      }
    )
  }

  def home = Action { implicit request =>
    // val users = Users.getAll()
    Ok(views.html.home())
  }

  def about = Action { implicit request =>
    Ok(views.html.about())
  }

  def faq = Action { implicit request =>
    Ok(views.html.faq())
  }

  def store = Action { implicit request =>
    Ok(views.html.store())
  }

}
