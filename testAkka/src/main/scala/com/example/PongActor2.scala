package com.example

import akka.actor.{Actor, ActorLogging, Props}

class PongActor2 extends Actor with ActorLogging {
  import PongActor2._

  def receive = {
  	case PingActor1.PingMessage(text) =>
  	  log.info("In PongActor - received message: {}", text)
  	  sender() ! PongMessage("pong")
  }
}

object PongActor2 {
  val props = Props[PongActor2]
  case class PongMessage(text: String)
}
