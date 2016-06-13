package models.tables

import javax.inject.{ Inject, Singleton }
import scala.concurrent.Future
import slick.profile.RelationalProfile
import play.api.db.slick. { DatabaseConfigProvider, HasDatabaseConfigProvider }
import scala.concurrent.ExecutionContext.Implicits.global
import models._

@Singleton
class Users @Inject() (
 val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[RelationalProfile] {
  import slick.driver.PostgresDriver.api._
  import slick.jdbc.GetResult

  /**
    * Table Definition
    * @param tag
    */
  class UsersTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def first_name = column[String]("first_name", O.Length(255, true))
    def last_name = column[String]("last_name", O.Length(255, true))
    def username = column[String]("username", O.Length(255, true))
    def password = column[String]("password", O.Length(255, true))

    def * = (id.?, first_name, last_name, username, password) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[UsersTable]

  def getAll(): Future[Seq[User]] =
    db.run(users.result)

  def add(user: User): Future[Boolean] =
    db.run(users += user).map(_ > 0)

  def findByUserName(username: String): Future[Option[User]] =
    db.run(users.filter(_.username === username).result.headOption)

  def findById(id: Int): Future[Option[User]] =
    db.run(users.filter(_.id === id).result.headOption)

  def findByUserNameAndPassword(username: String, password: String): Future[Option[User]] =
    db.run(users.filter(user => (user.username === username && user.password === password)).result.headOption)

}