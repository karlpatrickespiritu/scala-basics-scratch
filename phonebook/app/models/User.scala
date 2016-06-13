package models

case class User(
  id: Option[Int],
  first_name: String,
  last_name: String,
  username: String,
  password: String
)