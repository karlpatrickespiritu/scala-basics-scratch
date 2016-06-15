package models.tables

import javax.inject.{ Inject, Singleton }
import scala.concurrent.Future
import slick.profile.RelationalProfile
import play.api.db.slick. { DatabaseConfigProvider, HasDatabaseConfigProvider }
import scala.concurrent.ExecutionContext.Implicits.global
import models._
import models.tables.Users

@Singleton
class Contacts @Inject() (
 val users: Users,
 val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[RelationalProfile] {
  import slick.driver.PostgresDriver.api._
  import slick.jdbc.GetResult

  /**
    * Table Definition
    * @param tag
    */
  class ContactsTable(tag: Tag) extends Table[Contact](tag, "contacts") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Int]("user_id")
    def firstName = column[String]("first_name", O.Length(255, true))
    def lastName = column[String]("last_name", O.Length(255, true))
    def phone = column[String]("phone", O.Length(255, true))

    def owner = foreignKey("fk_userId", userId, users.UsersQuery)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

    def * = (id.?, userId, firstName, lastName, phone) <> (Contact.tupled, Contact.unapply)
  }

  /**
    * Contacts Table Query
    */
  object ContactsQuery extends TableQuery(new ContactsTable(_)) {
    def insert(contact: Contact) = this += contact
    def getAll = this.sortBy(_.id.desc).result
    def findById(id: Int) = this.filter(_.id === id)
    def findByUserId(userId: Int) = this.filter(_.userId === userId).sortBy(_.id.desc)
    def findByFirstName(firstName: String) = this.filter(_.firstName === firstName)
    def findByLastName(lastName: String) = this.filter(_.lastName === lastName)
    def findByFirstNameAndLastName(firstName: String, lastName: String) = this.filter(contact => (contact.firstName === firstName && contact.lastName === lastName))
  }

  def update(contact: Contact): Future[Boolean] =
    db.run(ContactsQuery.findById(contact.id.get).update(contact).map(_ > 0))

  def getAll(): Future[Seq[Contact]] =
    db.run(ContactsQuery.getAll)

  def add(contact: Contact): Future[Boolean] =
    db.run(ContactsQuery.insert(contact)).map(_ > 0)

  def deleteById(id: Int): Future[Boolean] =
    db.run(ContactsQuery.findById(id).delete.map(_ > 0))

  def findById(id: Int): Future[Option[Contact]] =
    db.run(ContactsQuery.findById(id).result.headOption)

  def findByUserId(userId: Int): Future[Seq[Contact]] =
    db.run(ContactsQuery.findByUserId(userId).result)

  def findByFirstNameAndLastName(firstName: String, lastName: String): Future[Option[Contact]] =
    db.run(ContactsQuery.findByFirstNameAndLastName(firstName, lastName).result.headOption)

}