package com.example

import akka.actor.{Actor, ActorLogging, Props}

class PingActor1 extends Actor with ActorLogging {
  import PingActor1._

  var counter = 0
  val pongActor = context.actorOf(PongActor2.props, "pongActor")

  def receive = {
  	case Initialize =>
	    log.info("In PingActor - starting ping-pong")
  	  pongActor ! PingMessage("ping")
  	case PongActor2.PongMessage(text) =>
  	  log.info("In PingActor - received message: {}", text)
  	  counter += 1
  	  if (counter == 3) context.system.shutdown()
  	  else sender() ! PingMessage("ping")
  }
}

object PingActor1 {
  val props = Props[PingActor1]
  case object Initialize
  case class PingMessage(text: String)
}
