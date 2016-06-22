package actors.chat

import akka.actor._
import akka.stream._
import Messages._

object ClientActor {
  def props(out: ActorRef, username: String) = Props(new ClientActor(out, username))
}

class ClientActor(out: ActorRef, username: String) extends Actor with ActorLogging {

//  override def preStart() = {
//    out ! Initialize
//  }

  def receive = {
    case Message(message) =>
      out ! Message(username + ": " + message)
    case message: String =>
      out ! message
    case _ =>
      println("???")
  }
}