package models

case class Contact(
  id: Option[Int],
  first_name: String,
  last_name: String,
  phone: String
)