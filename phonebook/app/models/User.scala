package models

case class User(
  id: Option[Int],
  firstName: String,
  lastName: String,
  userName: String,
  password: String
) {
  val fullName = firstName.concat(s" ${lastName}")
}