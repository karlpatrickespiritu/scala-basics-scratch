package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.{ I18nSupport, MessagesApi, Messages }
import play.api.data._
import play.api.data.Forms._
import play.api.libs.streams._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }
import models._
import forms._

import akka.actor._
import akka.stream._
import actors.chat.ChatSocketActor

@Singleton
class ChatController @Inject()(
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val materializer: Materializer,
  implicit val system: ActorSystem,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {

  def index = Action { implicit request =>
    Ok(views.html.chat.index());
  }

  def socket = WebSocket.accept[String, String] { implicit request =>
      ActorFlow.actorRef(out => ChatSocketActor.props(out))
  }

//  def socket = WebSocket.acceptOrResult[String, String] { implicit request =>
//    Future.successful(request.session.get("connected").map { userId =>
//      Right(ActorFlow.actorRef(ChatSocketActor.props))
//    }.getOrElse {
//      Left(Forbidden)
//    })
//  }

}