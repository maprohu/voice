package voice.rpi.core

import akka.actor.{Actor, ActorRef}
import akka.util.ByteString
import toolbox8.akka.actor.Target
import voice.rpi.core.FeedbackActor.InvalidInput

/**
  * Created by maprohu on 02-11-2016.
  */
class HidPhysicalActor extends Actor {

  var feedback = Target()
  var out = Target()

  case class State(
    buffer: ByteString
  )

  var state = State(ByteString.empty)

  def process(
    bs: ByteString
  ) : Unit = {
    import VoiceParser._
    val (drop, keep) = bs.span(_ != FirstByteConstantValue)

    if (!drop.isEmpty) {
      feedback.send(
        InvalidInput(drop)
      )
    }

    if (keep.length >= 3) {
      val (head, tail) = keep.splitAt(3)

      val ce = decodePhysical(head)

      if (ce == Unknown) {
        feedback.send(
          InvalidInput(head)
        )
        process(
          keep.tail
        )
      } else {
        out.send(ce)
        process(tail)
      }
    } else {
      state = State(keep)
    }

  }

  override def receive: Receive = {
    case bs : ByteString =>
      process(state.buffer ++ bs)
  }
}
