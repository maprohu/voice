package voice.akka

import akka.actor.{Actor, ActorRef, Props}
import toolbox8.akka.actor.{GetComponents, PubSubActor}

/**
  * Created by maprohu on 11-11-2016.
  */
class VoiceAppActor extends Actor {
  import VoiceAppActor._

  val feedback = context.actorOf(
    Props(
      classOf[FeedbackActor],
      FeedbackActor.Config()
    )
  )

  val controller = context.actorOf(
    Props(
      classOf[VoiceControllerActor],
      VoiceControllerActor.Config()
    )
  )

  val logical = context.actorOf(
    Props(
      classOf[HidLogicalActor],
      HidLogicalActor.Config(
        output = controller,
        feedback = feedback
      )
    )
  )

  val physical = context.actorOf(
    Props(
      classOf[HidPhysicalActor],
      HidPhysicalActor.Config(
        output = logical,
        feedback = feedback
      )
    )
  )

  val physicalSubject = context.actorOf(
    Props(
      classOf[PubSubActor]
    )
  )


  override def receive: Receive = {
    case GetComponents =>
      sender ! Components(
        feedback = feedback,
        controller = controller,
        logical = logical,
        physical = physical
      )
  }
}

object VoiceAppActor {

  case class Components(
    feedback: ActorRef,
    controller: ActorRef,
    logical: ActorRef,
    physical: ActorRef
  )

}
