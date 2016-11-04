package voice.rpi.core

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.util.ByteString
import voice.rpi.core.VoiceParser.ControllerEvent
import scala.collection.immutable._

/**
  * Created by maprohu on 04-11-2016.
  */
class FeedbackActor extends Actor {
  import FeedbackActor._

  var state : State = Normal

  override def receive: Receive = {
    case _ =>
  }
}


object FeedbackActor {
  sealed trait State
  case object Normal extends State
  case class Processing(
    keys: Set[Any] = Set.empty,
    queue: Seq[Feedback] = Seq.empty
  )


  sealed trait Feedback
  case class InvalidInput(data: ByteString) extends Feedback
  case class InvalidPhysicalInput(
    state: HidLogicalActor.State,
    event: ControllerEvent
  ) extends Feedback


}
