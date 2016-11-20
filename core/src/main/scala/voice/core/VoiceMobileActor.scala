package voice.core

import akka.actor.{Actor, Props}
import akka.event.Logging
import toolbox8.akka.actor.{DumpActor, DumpLogActor, PubSubActor, PubSubPublisherActor}
import voice.core.VoiceMobileActor.Config

/**
  * Created by maprohu on 20-11-2016.
  */
class VoiceMobileActor(
  config: Config
) extends Actor {
  import config._
  val log = Logging(context.system, this)


  val feedback = context.actorOf(
    Props(
      classOf[DumpLogActor]
    )
  )

  val natoActor = context.actorOf(
    Props(
      classOf[RecordNatoActor],
      RecordNatoActor.Config(
      )
    )
  )

  val hidLogicalActor = context.actorOf(
    Props(
      classOf[HidLogicalActor],
      HidLogicalActor.Config(
        output = natoActor,
        feedback = feedback
      )
    )
  )

  val hidPhysicalActor = context.actorOf(
    Props(
      classOf[HidPhysicalActor],
      HidPhysicalActor.Config(
        output = hidLogicalActor,
        feedback = feedback
      )
    )
  )

  override def receive: Receive = {
    case msg =>
      log.info("mobile: {}", msg)

  }


}

object VoiceMobileActor {
  case class Config()

}
