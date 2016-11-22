package voice.akka

import akka.actor.Status.Failure
import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import akka.stream.ThrottleMode.Shaping
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.stream.{ActorMaterializer, KillSwitches}
import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import monix.execution.Cancelable
import monix.execution.cancelables.CompositeCancelable
import toolbox6.logging.LogTools
import FeedbackActor.InvalidInput
import voice.core.events.Unknown

import scala.concurrent.duration._

/**
  * Created by maprohu on 02-11-2016.
  */
class HidPhysicalActor(
  config: HidPhysicalActor.Config
) extends Actor with LazyLogging with LogTools {
  import config._
  import HidPhysicalActor._
  import context.dispatcher
  val log = Logging(context.system, this)
//  import context.dispatcher
  implicit val materializer = ActorMaterializer.create(context.system)

//  var feedback = Target()
//  var out = Target()

  case class State(
    buffer: ByteString
  )

  var state = State(ByteString.empty)
  val cancellable = CompositeCancelable()


  def startReading() = {
    log.info("start hid reading")

    Source
      .repeat()
      .throttle(1, 5.seconds, 1, Shaping)
      .flatMapConcat({ _ =>
        log.info("read attempt starting")
        VoiceHid.hidSource
      })
      .viaMat(
        KillSwitches.single
      )(Keep.right)
      .to(Sink.actorRef(self, StreamComplete))
      .mapMaterializedValue({ kill =>
        cancellable += Cancelable({ () =>
          log.info("stopping hid stream")
          quietly {
            kill.shutdown()
          }
        })
      })
      .run()

  }

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    log.info("starting hid physical")

    startReading
  }

  def process(
    bs: ByteString
  ) : Unit = {
    import VoiceParser._
    import voice.core.HidParser._
    val (drop, keep) = bs.span(_ != FirstByteConstantValue)

    if (!drop.isEmpty) {
      feedback ! InvalidInput(drop)
    }

    if (keep.length >= 3) {
      val (head, tail) = keep.splitAt(3)

      val ce = VoiceParser.decodePhysical(head)

      if (ce == Unknown) {
        feedback ! InvalidInput(head)
        process(
          keep.tail
        )
      } else {
        output ! PhysicalEvent(
          ce
        )
        process(tail)
      }
    } else {
      state = State(keep)
    }

  }

  override def receive: Receive = {
    case bs : ByteString =>
      log.debug("hid input: {}", bs)
      process(state.buffer ++ bs)
    case Stop =>
      log.info("received Stop")
      cancellable.cancel()

    case StreamComplete =>
      log.info("stream is complete, stopping")
      context.stop(self)

    case f : Failure =>
      log.warning("error in stream: {}", f)
      context.stop(self)

    case other =>
      log.warning("unknown message: {}", other)

  }

  override def postStop(): Unit = {
    super.postStop()
    log.info("stopping hid actor")
    cancellable.cancel()
    quietly { materializer.shutdown() }
  }
}

object HidPhysicalActor {

  case class Config(
    output: ActorRef,
    feedback: ActorRef
  )

  case object Stop
  case object StreamComplete



//  case object StartHid


}