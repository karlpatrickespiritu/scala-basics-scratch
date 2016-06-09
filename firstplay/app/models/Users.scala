package models

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.i18n.{ I18nSupport, MessagesApi }

import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }

object Users {
  def getAll(): List[User] = users

  var users = List(
    User("Karl", 18),
    User("Kevin", 18),
    User("JC", 18),
    User("Notnot", 18),
    User("Mikel", 18),
    User("Cams", 18)
  )

  case class User(private var name: String = "", private var age: Int = 0) {
    protected val id = User.generateId()

    def getName(): String  = { this.name }

    def getAge(): Int  = { this.age }

    def getId(): Int  = { this.id }

    override def toString(): String = {
      s"${this.name} is ${this.age} years old"
    }
  }

  object User {
    private var id = 0

    def generateId(): Int = { id += 1; id }
  }
}