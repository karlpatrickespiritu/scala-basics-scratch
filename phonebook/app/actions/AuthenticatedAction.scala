package actions

import javax.inject._

import models.User
import models.tables.Users
import play.api._
import play.api.mvc._
import play.api.mvc.Result

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.profile.RelationalProfile
import play.api.db.slick. { DatabaseConfigProvider, HasDatabaseConfigProvider }

class AuthenticatedAction @Inject() (
  val Users: Users
) extends ActionBuilder[AuthenticatedRequest] {
  import slick.driver.PostgresDriver.api._
  import slick.jdbc.GetResult

  def invokeBlock[A](request: Request[A], block: AuthenticatedRequest[A] => Future[Result]): Future[Result] = {
    request.session.get("connected").map { userId =>
      Users.findById(userId.toInt).map {
        case Some(user) => block(new AuthenticatedRequest(user, request))
        case None => Future.successful(Results.Forbidden)
      }
    }.getOrElse(Future.successful(Results.Forbidden))
    Future.successful(Results.Forbidden)
  }

}