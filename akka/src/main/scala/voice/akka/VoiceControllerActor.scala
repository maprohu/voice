package voice.akka

import akka.actor.Actor
import akka.event.Logging

/**
  * Created by maprohu on 12-11-2016.
  */
class VoiceControllerActor(
  config: VoiceControllerActor.Config
) extends Actor {
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case msg =>
      log.info("controller: {}", msg)
  }
}

object VoiceControllerActor {
  case class Config()
}
