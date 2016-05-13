package services

import akka.actor.{Actor, ActorRef, Props}
import play.api.Logger
import play.api.libs.json.Json

class KafkaStreamer(out: ActorRef) extends Actor {

  def receive = {
    case "subscribe" =>
      Logger.info("Received subscription from a client")
      out ! Json.obj("text" -> "Hello, world!")
  }

}

object KafkaStreamer {
  def props(out: ActorRef) = Props(new KafkaStreamer(out))
}