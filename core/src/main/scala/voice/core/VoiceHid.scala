package voice.core

import java.io.{File, FileInputStream}
import java.nio.file._

import akka.actor.Scheduler
import akka.event.Logging
import akka.stream.ThrottleMode.Shaping
import akka.stream._
import akka.stream.scaladsl.{Flow, Keep, Sink, Source, StreamConverters}
import akka.util.ByteString
import boopickle.DefaultBasic.PicklerGenerator
import com.typesafe.scalalogging.{LazyLogging, StrictLogging}
import monix.execution.atomic.Atomic
import monix.execution.cancelables.{CompositeCancelable, MultiAssignmentCancelable}
import toolbox8.akka.stream.{Flows, Sources}
import toolbox8.common.FilesTools
import voice.audio.Talker

import scala.collection.immutable._
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * Created by maprohu on 29-10-2016.
  */
object VoiceHid extends LazyLogging {

  val HidFileName = "bt4ok"
  val DevPath = Paths.get("/dev")
  val HidFilePath = DevPath.resolve(HidFileName)

  val hidSource = {
    StreamConverters
      .fromInputStream(
        () => new FileInputStream(HidFilePath.toFile)
      )
  }

  def read(implicit
    executionContext: ExecutionContext
  ) : Source[ByteString, CompositeCancelable] = {
    Sources
      .singleMaterializedValue(() => CompositeCancelable())
      .flatMapConcat({ cancelSource =>
        logger.info("hid stream initializing")

        val cancelWaitFile = MultiAssignmentCancelable()
        cancelSource += cancelWaitFile

        Source
          .repeat()
          .mapAsync(1)({ _ =>
            logger.info("waiting for hid device file")
            val fut = FilesTools.waitForFile(
              HidFilePath
            )
            cancelWaitFile := fut
            fut
          })
          .takeWhile(identity)
          .throttle(1, 3.seconds, 1, Shaping)
          .flatMapConcat({ _ =>
            if (HidFilePath.toFile.exists()) {
              logger.info("start reading hid device file")
              StreamConverters
                .fromInputStream(
                  () => new FileInputStream(HidFilePath.toFile)
                )
                .mapMaterializedValue({ m =>
                  m
                    .onComplete(r => logger.info("read result: {}", r))
                })
            } else {
              Source.empty
            }
          })
          .withAttributes(ActorAttributes.supervisionStrategy(Supervision.resumingDecider))
          .via(
            Flows
              .stopper
              .mapMaterializedValue({ c =>
                cancelSource += c
              })
          )
        })


  }

}

class VoiceHid(implicit
  materializer: Materializer
) extends StrictLogging {
  import VoiceHid._
  import materializer.executionContext

  private val cancelSource = CompositeCancelable()

  private val cancelWaitFile = MultiAssignmentCancelable()
  cancelSource += cancelWaitFile


  private val completed =
    Source
      .repeat()
      .mapAsync(1)({ _ =>
        val fut = FilesTools.waitForFile(
          HidFilePath
        )
        cancelWaitFile := fut
        fut
      })
      .takeWhile(identity)
      .throttle(1, 3.seconds, 1, Shaping)
      .flatMapConcat({ _ =>
        StreamConverters
          .fromInputStream(
            () => new FileInputStream(HidFilePath.toFile)
          )
      })
      .withAttributes(ActorAttributes.supervisionStrategy(Supervision.resumingDecider))
      .via(
        Flows
          .stopper
          .mapMaterializedValue({ c =>
            cancelSource += c
          })
      )
      .mapAsync(1)({ e =>
        queue.offer(e)
      })
      .toMat(
        Sink.ignore
      )(Keep.right)
      .run()



  private val (queue, publisher) =
    Source
      .queue[ByteString](0, OverflowStrategy.backpressure)
      .toMat(
        Sink.asPublisher(true)
      )(Keep.both)
      .run()

  val input = Source.fromPublisher(publisher)

  def stop() = {
    logger.info("stopping hid source")
    cancelSource.cancel()

    completed
      .onComplete({ _ =>
        logger.info("stopping hid queue")
        queue.complete()
      })
  }

}

object VoiceParser extends StrictLogging {
  import events._

  val LongClickDuration = 500.millis

  val PacketSize = 3
  val FirstByteConstantValue : Byte = 4

  val AxisCenter = 1
  val AxisNegative = 2
  val AxisPositive = 0

  val PosVerticalAxis = 0
  val PosHorizontalAxis = 2

  val PosA = 8
  val PosB = 4
  val PosC = 7
  val PosD = 5
  val PosHigh = 11
  val PosLow = 10



  sealed trait Outcome
  case object Click extends Outcome
  case object LongClick extends Outcome



  val Noop = () => ()

  case class ScanState(
    next: ControllerEvent => ScanState,
    out: Source[LogicalEvent, _] = Source.empty
  )

  def decodePhysical(
    bs3: ByteString
  ) : ControllerEvent = {
    decodePhysical(bs3(1), bs3(2))
  }

  def decodePhysical(
    b1: Byte,
    b2: Byte
  ) : ControllerEvent = {
    val bits = (b2 << 8) | (b1 & 0xFF)

    bits match {
      case 0x0005 => Released
      case 0x0105 => ButtonA
      case 0x0015 => ButtonB
      case 0x0085 => ButtonC
      case 0x0025 => ButtonD
      case 0x0405 => ButtonLow
      case 0x0805 => ButtonHigh
      case 0x0009 => JoystickLeft
      case 0x0001 => JoystickRight
      case 0x0006 => JoystickDown
      case 0x0004 => JoystickUp
      case 0x000a => JoystickDownLeft
      case 0x0002 => JoystickDownRight
      case 0x0008 => JoystickUpLeft
      case 0x0000 => JoystickUpRight
      case _ => Unknown
    }
  }


}

class VoiceParser(
  dir: File,
  scheduler: Scheduler
)(implicit
  executionContext: ExecutionContext,
  sch: monix.execution.Scheduler
) {
  import events._
  import VoiceParser._

  val voiceController = new VoiceController(
    new Talker(new File(dir, "talker"))
  )
  val EmptyState = emptyState()

  def emptyState(out: Source[LogicalEvent, _] = Source.empty) : ScanState = {
    ScanState(
      out = out,
      next = { e =>
        e match {
          case Released =>
            EmptyState
          case b : ButtonEvent =>
            buttonState(b)
          case j : JoystickEvent =>
            joysticState(j)
          case _ =>
            EmptyState
        }
      }
    )
  }

  def joysticState(
    joystickEvent: JoystickEvent
  ) : ScanState = {
    ScanState(
      next = { e =>
        e match {
          case Released =>
            emptyState(
              Source.single(Released)
            )
          case j : JoystickEvent =>
            joysticState(j)
          case b : ButtonEvent =>
            buttonState(b)
          case _ =>
            EmptyState
        }
      },
      out = Source.single(joystickEvent)

    )
  }

  def buttonState(
    buttonEvent: ButtonEvent,
    out: Source[LogicalEvent, _] = Source.empty
  ) : ScanState = {
    val promise = Promise[Outcome]()

    scheduler.scheduleOnce(
      LongClickDuration
    )({
      promise.trySuccess(LongClick)
    })

    ScanState(
      next = { e =>
        promise.trySuccess(Click)

        EmptyState.next(e)
      },
      out =
        out.concat(
          Source
            .fromFuture(
              promise.future
            )
            .map({
              case Click =>
                LogicalClick(buttonEvent)
              case LongClick =>
                LogicalLongClick(buttonEvent)
            })

        )
    )

  }


  val invalidModeTalker = Atomic(Future.successful())

  val Parser =
    Flow[ByteString]
      .log("hid_bytes").withAttributes(Attributes.logLevels(onElement = Logging.DebugLevel))
      .statefulMapConcat({ () =>
        var buffer = ByteString.empty

      { bs =>
        val total = buffer ++ bs

        val (out, left) =
          total.splitAt(total.size - total.size % PacketSize)

        buffer = left

        out
          .grouped(PacketSize)
          .to[Iterable]
      }
      })
      .log("hid_bytes3").withAttributes(Attributes.logLevels(onElement = Logging.DebugLevel))
      .map({ bs3 =>
        if (bs3(0) != FirstByteConstantValue) {
          invalidModeTalker
            .transform({ f =>
              if (f.isCompleted) {
                voiceController
                  .talker
                  .cached("incorrect controller mode")
              } else {
                f
              }
            })

          Unknown
        } else {
          decodePhysical(bs3)
        }
      })
      .statefulMapConcat({ () =>
        var last : ControllerEvent = Released

      { e =>
        if (last == e) Iterable.empty
        else {
          last = e
          Iterable(e)
        }
      }
      })
      .scan(EmptyState)({ (acc, elem) =>
        acc.next(elem)
      })
      .buffer(1, OverflowStrategy.backpressure)
      .flatMapConcat(_.out)
      .map({ e =>
        voiceController.feedback(e)
        e
      })

}





//              val str = Integer.toBinaryString(0x10000 + bits).drop(1)
//              val str = Integer.toHexString(0x10000 + bits).drop(1)
//              val horizontal = (bits >> PosHorizontalAxis) & 3
//              val vertical = (bits >> PosVerticalAxis) & 3
//              println(
//                s"0x${str}"
////                str.grouped(4).mkString(" ")
//              )
