package models.tables

import javax.inject.{ Inject, Singleton }
import scala.concurrent.Future
import slick.profile.RelationalProfile
import play.api.db.slick. { DatabaseConfigProvider, HasDatabaseConfigProvider }
import scala.concurrent.ExecutionContext.Implicits.global
import models._

@Singleton
class Contacts @Inject() (
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
    def first_name = column[String]("first_name", O.Length(255, true))
    def last_name = column[String]("last_name", O.Length(255, true))
    def phone = column[String]("phone", O.Length(255, true))

    def * = (id.?, first_name, last_name, phone) <> (Contact.tupled, Contact.unapply)
  }

  val contacts = TableQuery[ContactsTable]

  def update(contact: Contact): Future[Boolean] =
    db.run(contacts.filter( _.id === contact.id ).update(contact).map(_ > 0))

  def getAll(): Future[Seq[Contact]] =
    db.run(contacts.result)

  def add(contact: Contact): Future[Boolean] =
    db.run(contacts += contact).map(_ > 0)

  def deleteById(id: Int): Future[Boolean] =
    db.run(contacts.filter( _.id === id ).delete.map(_ > 0))

  def findById(id: Int): Future[Option[Contact]] =
    db.run(contacts.filter( _.id === id ).result.headOption)

}