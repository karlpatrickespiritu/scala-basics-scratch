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
    * Users Table Query
    */
  object UsersQuery extends TableQuery(new UsersTable(_)) {
    def insert(user: User) = this += user
    def findById(id: Int) = this.filter(_.id === id)
    def findByFirstName(firstName: String) = this.filter(_.first_name === firstName)
    def findByLastName(lastName: String) = this.filter(_.last_name === lastName)
    def findByUserName(userName: String) = this.filter(_.username === userName)
    def findByUserNameAndPassword(userName: String, password: String) = this.filter(user => (user.username === userName && user.password === password))
  }

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

  def getAll(): Future[Seq[User]] =
    db.run(UsersQuery.result)

  def add(user: User): Future[Boolean] =
    db.run(UsersQuery.insert(user)).map(_ > 0)

  def findByUserName(username: String): Future[Option[User]] =
    db.run(UsersQuery.findByUserName(username).result.headOption)

  def findById(id: Int): Future[Option[User]] =
    db.run(UsersQuery.findById(id).result.headOption)

  def findByUserNameAndPassword(userName: String, password: String): Future[Option[User]] =
    db.run(UsersQuery.findByUserNameAndPassword(userName, password).result.headOption)

}