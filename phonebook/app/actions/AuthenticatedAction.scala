package actions

import models.User
import play.api._
import play.api.mvc._

import scala.concurrent.Future

object AuthenticatedAction extends ActionBuilder[AuthenticatedRequest] {
  def invokeBlock[A](request: Request[A], block: AuthenticatedRequest[A] => Future[Result]): Future[Result] = {
    request.session.get("connected")
      .map { userId =>
        val user = User(Some(100), "test", "user", "testuser", "123")
        block(new AuthenticatedRequest(user, request))
      }
      .getOrElse(Future.successful(Results.Forbidden))
  }
}