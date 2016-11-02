package voice.core

import akka.actor.{ActorSystem, Props}
import toolbox8.akka.actor.PubSubActor

/**
  * Created by maprohu on 02-11-2016.
  */
class VoiceSubjects(
  system: ActorSystem
) {

  val hidBytes = system.actorOf(Props[PubSubActor])
  val hidPhysical = system.actorOf(Props[PubSubActor])
  val hidLogical = system.actorOf(Props[PubSubActor])

}


