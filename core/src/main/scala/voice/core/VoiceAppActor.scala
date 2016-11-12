package voice.core

import akka.actor.{Actor, Props}
import akka.actor.Actor.Receive
import toolbox8.akka.actor.PubSubActor
import voice.core.events.Picky

/**
  * Created by maprohu on 11-11-2016.
  */
class VoiceAppActor extends Actor {
  import VoiceAppActor._

  val physical = context.actorOf(
    Props(
      classOf[HidPhysicalActor]
    )
  )

  val physicalSubject = context.actorOf(
    Props(
      classOf[PubSubActor]
    )
  )


  override def receive: Receive = ???
}

object VoiceAppActor {

  case class Components(

  ) extends Picky

}
