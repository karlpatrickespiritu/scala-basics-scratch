package models.tables

import javax.inject.{ Inject, Singleton }
import scala.concurrent.Future
import slick.profile.RelationalProfile
import play.api.db.slick. { DatabaseConfigProvider, HasDatabaseConfigProvider }
import scala.concurrent.ExecutionContext.Implicits.global
import models.User

@Singleton
class Users @Inject()(
 val dbConfigProvider: DatabaseConfigProvider
 ) extends HasDatabaseConfigProvider[RelationalProfile] {
 import slick.driver.PostgresDriver.api._
 import slick.jdbc.GetResult

 // query part
 val query = TableQuery[UsersTable]

 def insert(user: User): Future[Boolean] =
  db.run(query += user).map(_ > 0)

 def list(): Future[Seq[User]] =
  db.run(query.result)

 def delete(id: Int): Future[Boolean] =
  db.run(query.filter( _.id === id ).delete.map(_ > 0))

 def findById(id: Int): Future[Option[User]] =
  db.run(query.filter( _.id === id ).result.headOption)

 def updateUser(user: User): Future[Boolean] =
  db.run(query.filter( _.id === user.id ).update(user).map(_ > 0))

 // table definition
 class UsersTable(tag: Tag) extends Table[User](tag, "USERS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def username = column[String]("USERNAME", O.Length(255, true))
  def password = column[String]("PASSWORD", O.Length(255, true))

  def * = (id.?, username, password) <> (User.tupled, User.unapply _)
 }
}