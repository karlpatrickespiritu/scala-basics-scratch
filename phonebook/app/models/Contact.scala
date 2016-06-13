package models

case class Contact(
  id: Option[Int],
  user_id: Int,
  first_name: String,
  last_name: String,
  phone: String
)