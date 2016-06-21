package actors.chat

import akka.actor._
import akka.stream._


object ChatSocketActor {
  def props(out: ActorRef) = Props(new ChatSocketActor(out))
}

class ChatSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      out ! ("I received your message: " + msg)
  }
}