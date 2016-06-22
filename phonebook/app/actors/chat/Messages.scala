package actors.chat

object Messages {
  case class Message(message: String)
  case class ServerMessage(message: String)
  object Initialize
}
