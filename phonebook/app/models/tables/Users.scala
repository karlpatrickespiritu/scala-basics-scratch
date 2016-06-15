package models.tables

import javax.inject.{ Inject, Singleton }
import scala.concurrent.Future
import slick.profile.RelationalProfile
import play.api.db.slick. { DatabaseConfigProvider, HasDatabaseConfigProvider }
import scala.concurrent.ExecutionContext.Implicits.global
import models._
import models.tables._

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
    def findByFirstName(firstName: String) = this.filter(_.firstName === firstName)
    def findByLastName(lastName: String) = this.filter(_.lastName === lastName)
    def findByUserName(userName: String) = this.filter(_.userName === userName)
    def findByUserNameAndPassword(userName: String, password: String) = this.filter(user => (user.userName === userName && user.password === password))
  }

  /**
    * Table Definition
    * @param tag
    */
  class UsersTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def firstName = column[String]("first_name", O.Length(255, true))
    def lastName = column[String]("last_name", O.Length(255, true))
    def userName = column[String]("username", O.Length(255, true))
    def password = column[String]("password", O.Length(255, true))

    def * = (id.?, firstName, lastName, userName, password) <> (User.tupled, User.unapply)
  }

  def getAll(limit: Int = 10, offset: Int = 0): Future[Seq[User]] =
    db.run(UsersQuery.drop(offset).take(limit).result)

  def add(user: User): Future[Boolean] =
    db.run(UsersQuery.insert(user)).map(_ > 0)

  def findByUserName(userName: String): Future[Option[User]] =
    db.run(UsersQuery.findByUserName(userName).result.headOption)

  def findById(id: Int): Future[Option[User]] =
    db.run(UsersQuery.findById(id).result.headOption)

  def findByUserNameAndPassword(userName: String, password: String): Future[Option[User]] =
    db.run(UsersQuery.findByUserNameAndPassword(userName, password).result.headOption)

}