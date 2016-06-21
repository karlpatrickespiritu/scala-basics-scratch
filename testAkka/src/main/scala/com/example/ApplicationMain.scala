package com.example

import akka.actor.{ActorSystem, Actor, ActorLogging, Props, ActorRef}

object ApplicationMain extends App {
  import Messages._
  val system = ActorSystem("MyActorSystem")
  //val pingActor = system.actorOf(Props[PingActor], "pingActor")
  val server = system.actorOf(Props[ServerActor], "server")

  val client = system.actorOf(Props(classOf[ClientActor],"cams",server), "client")

  val client2 = system.actorOf(Props(classOf[ClientActor],"not",server), "client2")

  client2 ! Message("e apil kog register bi")
  def cgeInputKuno(client: ActorRef): Unit = {
    val input = readLine()
    if(input != "exit"){
        client ! Message(input)
        cgeInputKuno(client)
      }else{
        system.shutdown
      }
  }

  cgeInputKuno(client)

  //pingActor ! Messages.Initialize
  // This example app will ping pong 3 times and thereafter terminate the ActorSystem -
  // see counter logic in PingActor
  //system.awaitTermination()
}



class PingActor extends Actor with ActorLogging{
  import Messages._

  var ctr = 0
  val pongActor =  context.actorOf(Props[PongActor], "pongActor")

  def receive = {
    case Initialize =>
      pongActor ! Message("ping")
    case Message(msg)  =>
      println(msg)
      context.system.shutdown()
    case _ => println("sumting wong")

  }

}

class PongActor extends Actor with ActorLogging{
  import Messages._
  def receive = {
    case Message(msg) =>
      sender !  Message("pong")
    case _ => println("sumting wong")
  }
}

class ClientActor(username: String, server: ActorRef) extends Actor with ActorLogging {
  import Messages._

  override def preStart() = {
    server ! Initialize
  }
  def receive = {
    case Message(msg) =>
      server ! Message(username+":"+msg)
    case ServerMessage(msg) =>
      println(msg)
    case _ => println("sumting wong in client")
  }
}

class ServerActor extends Actor with ActorLogging{
  import Messages._

  var users = Set[ActorRef]()
  def receive = {
    case Initialize =>
      users += sender
      sender ! ServerMessage("registered naka")
    case Message(msg) =>
      users.foreach( _ ! ServerMessage(msg) )
    case _ => println("sumting wong in server")
  }

}


object Messages {
  case class Message(msg: String)
  case class ServerMessage(msg: String)
  object Initialize
}
