package models

import play.api.data._
import play.api.data.Forms._

case class Contact(
 id: Option[Int],
 first_name: String,
 last_name: String,
 phone: String
)