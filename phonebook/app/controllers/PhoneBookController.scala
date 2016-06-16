package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.data._
import play.api.data.Forms._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import ejisan.play.libs.{ PageMetaApi, PageMetaSupport }
import forms._
import models._
import actions.AuthenticatedAction
import forms.{ ContactsForm }

@Singleton
class PhoneBookController @Inject() (
 val messagesApi: MessagesApi,
 val pageMetaApi: PageMetaApi,
 val Contacts: tables.Contacts,
 implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {

  val phoneBookPage = routes.PhoneBookController.index

  def testAction  = AuthenticatedAction { implicit request =>
    Ok(request.user + "")
  }

  def index = AuthenticatedAction.async { implicit request =>
    request.session.get("connected").map { userId =>
      Contacts.findByUserId(userId.toInt).map {
        contacts => Ok(views.html.phonebook.index(contacts))
      }
    }.getOrElse {
      Future.successful(Redirect(routes.AuthController.login()))
    }
  }

  def deleteById(id: Int) = AuthenticatedAction.async { implicit request =>
    Contacts.deleteById(id).map { _ => Redirect(phoneBookPage).flashing("message" -> "Contact has been deleted.") }
  }

  def add = AuthenticatedAction { implicit request =>
    Ok(views.html.phonebook.phonebookform())
  }

  def update(id: Int) = AuthenticatedAction.async { implicit request =>
    Contacts.findById(id).map {
      case Some(contact) => {
        Ok(views.html.phonebook.phonebookform(ContactsForm.contact.fill(contact)))
      }
      case None => Ok(views.html.httpcodes.code404())
    }
  }

  def formPost = AuthenticatedAction.async { implicit request =>
    ContactsForm.contact.bindFromRequest.fold(
      formErrors =>  {
        Future.successful(BadRequest(views.html.phonebook.phonebookform(formErrors)))
      },
      contact => {
        val isUpdate = !contact.id.isEmpty
        if (isUpdate) {
          Contacts.update(contact).map { _ => Redirect(routes.PhoneBookController.index()).flashing("message" -> s"`${contact.fullName}` has been updated.") }
        } else {
          Contacts.add(contact).map { _ => Redirect(routes.PhoneBookController.add()).flashing("message" -> s"`${contact.fullName}` has been created.") }
        }
      }
    )
  }

}
