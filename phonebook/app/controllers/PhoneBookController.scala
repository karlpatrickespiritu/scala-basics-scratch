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
import actions._
import forms.{ ContactsForm }

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class PhoneBookController @Inject() (
 val messagesApi: MessagesApi,
 val pageMetaApi: PageMetaApi,
 val Contacts: tables.Contacts,
 val Authenticate: actions.Authenticate,
 implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {

  val phoneBookPage = routes.PhoneBookController.index

  // TODO: ActionBuilder return
  def index = Authenticate.async { implicit request =>
    request.session.get("connected").map { userId =>
      Contacts.findByUserId(userId.toInt).map {
        contacts => Ok(views.html.phonebook.index(contacts))
      }
    }.getOrElse {
      Future.successful(Redirect(routes.AuthController.login()))
    }
  }

  def deleteById(id: Int) = Action.async { implicit request =>
    Contacts.deleteById(id).map { _ => Redirect(phoneBookPage).flashing("message" -> "Contact has been deleted.") }
  }

  def add = Authenticate { implicit request =>
    Ok(views.html.phonebook.phonebookform())
  }

  def update(id: Int) = Action.async { implicit request =>
    Contacts.findById(id).map {
      case Some(contact) => {
        Ok(views.html.phonebook.phonebookform(ContactsForm.contact.fill(contact)))
      }
      case None => Ok(views.html.httpcodes.code404())
    }
  }

  def formPost = Action.async { implicit request =>
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
