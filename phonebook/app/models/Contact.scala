package models

case class Contact(
  id: Option[Int],
  userId: Int,
  firstName: String,
  lastName: String,
  phone: String
) {
  val fullName = firstName.concat(s" ${lastName}")
}