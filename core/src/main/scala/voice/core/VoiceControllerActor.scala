package voice.core

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging
import voice.core.VoiceControllerActor.Config

/**
  * Created by maprohu on 12-11-2016.
  */
class VoiceControllerActor(
  config: Config
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
