package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.{ I18nSupport, MessagesApi, Messages }
import play.api.data._
import play.api.data.Forms._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }
import models._
import forms._

@Singleton
class AuthController @Inject()(
 val messagesApi: MessagesApi,
 val pageMetaApi: PageMetaApi,
 val Users: tables.Users,
 implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {

  def login = Action { implicit request =>
    Ok(views.html.auth.login(UsersForm.login));
  }

  def logout = Action { implicit request =>
    Redirect(routes.HomeController.index()).withNewSession
  }

  def registration = Action { implicit request =>
    Ok(views.html.auth.registration(UsersForm.user));
  }

  def loginSubmission = Action.async { implicit request =>
    UsersForm.login.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.auth.login(formWithErrors))),
      login => {
        Users.findByUserNameAndPassword(login.username, login.password).map {
          case Some(user) => Redirect(routes.HomeController.index()).withSession("connected" -> user.id.get.toString)
          case None => Redirect(routes.AuthController.login()).flashing("message" -> Messages("authcontroller.login.incorrect.username.password"))
        }
      }
    )
  }

  def userSubmission = Action.async { implicit request =>
    UsersForm.user.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.auth.registration(formWithErrors))),
      user => Users.add(user).map { _ => Redirect(routes.AuthController.login()) }
    )
  }

}