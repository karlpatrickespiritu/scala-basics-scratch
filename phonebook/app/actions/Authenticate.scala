package actions

import play.api.mvc._
import scala.concurrent._
import play.api.mvc.Results._

class Authenticate extends ActionBuilder[Request] {

  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    request.session.get("connected").map { user =>
      block(request)
    }.getOrElse {
      Future.successful(Redirect("/login"))
    }
  }

}