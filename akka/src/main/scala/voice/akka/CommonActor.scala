package voice.akka

import akka.actor.ActorRef

/**
  * Created by maprohu on 04-11-2016.
  */
object CommonActor {

  case class SetFeedback(ref: ActorRef)
  case class SetOutput(ref: ActorRef)

}
