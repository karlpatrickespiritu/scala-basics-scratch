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

  val contactForm = Form {
    mapping(
      "id" -> optional(number),
      "user_id" -> number,
      "first_name" -> nonEmptyText,
      "last_name" -> nonEmptyText,
      "phone" -> nonEmptyText
    )(Contact.apply) (Contact.unapply)
  }

  val phoneBookPage = routes.PhoneBookController.index()

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

  // TODO: ActionBuilder return
  def add = Authenticate { implicit request =>
    request.session.get("connected").map { userId =>
      Ok(views.html.phonebook.phonebookform(contactForm, userId.toInt))
    }.getOrElse {
      Redirect(routes.AuthController.login())
    }
  }

  def update(id: Int) = Action.async { implicit request =>
    Contacts.findById(id).map {
      case Some(contact) => Ok(views.html.phonebook.phonebookform(contactForm.fill(contact)))
      case None => Ok(views.html.httpcodes.code404())
    }
  }

  def formPost = Action.async { implicit request =>
    contactForm.bindFromRequest.fold(
      formErrors => Future.successful(BadRequest(views.html.phonebook.phonebookform(formErrors))),
      contact => {
        val name = contact.first_name + " " + contact.last_name
        val contactExits = !contact.id.isEmpty
        if (contactExits) {
          Contacts.update(contact).map { _ => Redirect(phoneBookPage).flashing("message" -> s"`${name}` has been updated.") }
        } else {
          Contacts.add(contact).map { _ => Redirect(phoneBookPage).flashing("message" -> s"`${name}` has been created.") }
        }
      }
    )
  }

}
