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

  val contacts = TableQuery[ContactsTable]

  def getAll(): Future[Seq[Contact]] =
    db.run(contacts.result)

  // table definition
  class ContactsTable(tag: Tag) extends Table[Contact](tag, "contacts") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def first_name = column[String]("first_name", O.Length(255, true))
    def last_name = column[String]("last_name", O.Length(255, true))
    def phone = column[String]("phone", O.Length(255, true))

    def * = (id.?, first_name, last_name, phone) <> (Contact.tupled, Contact.unapply _)
  }
}