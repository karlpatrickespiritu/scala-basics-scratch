package actors

import akka.actor.{ Actor, ActorLogging, Props }
import scala.concurrent.duration._
import scala.language.postfixOps

class BackgroundActor extends Actor with ActorLogging {
  import BackgroundActor._
  import context.{ dispatcher, system }

  // system.scheduler.schedule(0.seconds, 60.seconds)(self ! BackgroundMessage("Message"))

  def receive = {
    case BackgroundMessage(message) =>
      println(message)
  }
}

case object BackgroundActor {
  val props = Props[BackgroundActor]
  case class BackgroundMessage(message: String)
}