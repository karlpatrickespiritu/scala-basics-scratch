package forms

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

object ContactsForm {

  val contact = Form {
    mapping(
      "id" -> optional(number),
      "userId" -> number,
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "phone" -> nonEmptyText
    )(Contact.apply) (Contact.unapply)
  }

}