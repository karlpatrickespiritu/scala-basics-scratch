package actions

import play.api.mvc._
import scala.concurrent.Future

import models.User

class AuthenticatedRequest[A](user: User, val request: Request[A]) extends WrappedRequest[A](request)