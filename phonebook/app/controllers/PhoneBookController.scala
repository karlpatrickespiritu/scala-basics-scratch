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
class PhoneBookController @Inject() (
 val messagesApi: MessagesApi,
 val pageMetaApi: PageMetaApi,
 val Contacts: tables.Contacts,
 implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {

  val contactForm = Form {
    mapping(
      "id" -> optional(number),
      "first_name" -> nonEmptyText,
      "last_name" -> nonEmptyText,
      "phone" -> nonEmptyText
    )(Contact.apply) (Contact.unapply)
  }

  val phoneBookPage = routes.PhoneBookController.index()

  /**
    * Create an Action to render an HTML page.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action.async { implicit request =>
    Contacts.getAll().map {
      contacts => Ok(views.html.phonebook.index(contacts))
    }
  }

  def deleteById(id: Int) = Action.async { implicit request =>
    Contacts.deleteById(id).map { _ => Redirect(phoneBookPage).flashing("message" -> s"Contact has been deleted.") }
  }

  def add = Action { implicit request =>
    Ok(views.html.phonebook.phonebookform(contactForm))
  }

  def update(id: Int) = Action.async { implicit request =>
    Contacts.findById(id).map {
      contact => Ok(views.html.phonebook.phonebookform(contactForm.fill(contact.get)))
    }
  }

  def formPost = Action.async { implicit request =>
    contactForm.bindFromRequest.fold(
      formErrors => {
        Future.successful(BadRequest(views.html.phonebook.phonebookform(formErrors)))
      },
      contact => {
        val name = contact.first_name + " " + contact.last_name
        val contactExits = !contact.id.isEmpty
        if (contactExits)
          Contacts.update(contact).map { _ => Redirect(phoneBookPage).flashing("message" -> s"`${name}` has been updated.") }
        else
          Contacts.add(contact).map { _ => Redirect(phoneBookPage).flashing("message" -> s"`${name}` has been created.") }
      }
    )
  }

}
