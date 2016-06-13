package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.data._
import play.api.data.Forms._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }
import models._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class AuthController @Inject()(
 val messagesApi: MessagesApi,
 val pageMetaApi: PageMetaApi,
 val Users: tables.Users,
 implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {

  val userForm = Form {
    mapping(
      "id" -> optional(number),
      "first_name" -> nonEmptyText,
      "last_name" -> nonEmptyText,
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    ) (User.apply) (User.unapply)
  }

  val loginForm = Form {
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    ) (Login.apply) (Login.unapply)
  }

  def login = Action { implicit request =>
    Ok(views.html.auth.login(loginForm));
  }

  def logout = Action { implicit request =>
    Redirect(routes.HomeController.index()).withNewSession
  }

  def registration = Action { implicit request =>
    Ok(views.html.auth.registration(userForm));
  }

  def loginSubmission = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formErrors => {
        Future.successful(BadRequest(views.html.auth.login(loginForm)))
      },
      login => {
        Users.findByUserNameAndPassword(login.username, login.password).map {
          case Some(user) => Redirect(routes.HomeController.index()).withSession("connected" -> user.id.get.toString)
          case None => Redirect(routes.AuthController.login()).flashing("message" -> "Incorrect credentials. Please try agai")
        }
      }
    )
  }

  def userSubmission = Action.async { implicit request =>
    userForm.bindFromRequest.fold(
      formErrors => {
        Future.successful(BadRequest(views.html.auth.registration(formErrors)))
      },
      user => {
        Users.add(user).map { _ => Redirect(routes.AuthController.login()) }
      }
    )
  }

}