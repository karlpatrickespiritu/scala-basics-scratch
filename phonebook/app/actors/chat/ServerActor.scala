package actors.chat

import akka.actor._
import akka.stream._
import Messages._
import play.api.libs.json.JsValue

object ServerActor {
  def props(out: ActorRef) = Props(new ServerActor(out))
}

class ServerActor(out: ActorRef) extends Actor with ActorLogging {
  var users = Set[ActorRef]()

  def receive = {
    case Initialize =>
      users += out
      out ! ServerMessage("registered")
    case Message(message) =>
      out ! ServerMessage(message)
    case message: String =>
      out ! (message)
    case message: JsValue =>
      out ! (JsValue)
  }
}