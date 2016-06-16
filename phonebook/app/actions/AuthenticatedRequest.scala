package actions

import play.api.mvc._
import scala.concurrent.Future
import play.api.mvc.{ Request, WrappedRequest }

import models.User

class AuthenticatedRequest[A](val user: User, val request: Request[A])
  extends WrappedRequest[A](request)