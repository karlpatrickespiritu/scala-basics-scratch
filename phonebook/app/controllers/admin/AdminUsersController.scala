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
import actions._
import forms._

@Singleton
class AdminUsersController @Inject()(
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  val AuthenticatedAction: AuthenticatedAction, // v2
  val Authenticate: Authenticate, // v1
  val Users: tables.Users,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {

  def index = Authenticate.async { implicit request =>
    Users.getAll().map {
      users => Ok(views.html.admin.users.index(users))
    }
  }

  def viewUser(id: Int) = Authenticate.async { implicit require =>
    Users.findById(id).map {
      case Some(user) => Ok(views.html.admin.users.view(user))
      case None => Ok(views.html.httpcodes.code404())
    }
  }

}