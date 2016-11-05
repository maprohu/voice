package voice.core

import java.io.{File, FileInputStream}
import java.nio.file._

import akka.actor.Scheduler
import akka.event.Logging
import akka.stream.ThrottleMode.Shaping
import akka.stream._
import akka.stream.scaladsl.{Flow, Keep, Sink, Source, StreamConverters}
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import monix.execution.atomic.Atomic
import monix.execution.cancelables.{CompositeCancelable, MultiAssignmentCancelable}
import toolbox8.akka.stream.Flows
import toolbox8.common.FilesTools
import voice.audio.Talker

import scala.collection.immutable._
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * Created by maprohu on 29-10-2016.
  */
object VoiceHid {

  val HidFileName = "bt4ok"
  val DevPath = Paths.get("/dev")
  val HidFilePath = DevPath.resolve(HidFileName)

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


//    def connectToHid(): Unit = {
//      def connectToExisting() : Unit = {
//        StreamConverters
//          .fromInputStream(
//            () => new FileInputStream(HidFilePath.toFile)
//          )
//          .mergeMat(
//            Source.maybe,
//            eagerComplete = true
//          )(Keep.right)
//          .mapAsync(1)({ e =>
//            queue.offer(e)
//          })
//          .watchTermination()({ (promise, done) =>
//            val c = Cancelable({ () =>
//              logger.info("stopping hid")
//              promise.trySuccess(None)
//            })
//            cancel += c
//            done.onComplete({ o =>
//              logger.info("hid reading complete: {}", o)
//              cancel -= c
//            })
//            done.onFailure({
//              case ex =>
//                logger.warn("hid failed", ex)
//                connectToHid()
//            })
//          })
//          .to(Sink.ignore)
//          .run()
//      }
//
//      logger.info("creating dev watcher")
//      val watcher = DevPath.getFileSystem().newWatchService()
//      logger.info("registering dev watcher")
//      DevPath.register(
//        watcher,
//        StandardWatchEventKinds.ENTRY_CREATE
//      )
//      logger.info("watcher registered")
//      if (HidFilePath.toFile.exists()) {
//        logger.info(s"${HidFilePath} exists, start reading")
//        watcher.close()
//        connectToExisting()
//      } else {
//        logger.info(s"${HidFilePath} does not exists, start watching")
//        new Thread() {
//          override def run(): Unit = {
//            while (!HidFilePath.toFile.exists()) {
//              logger.info("polling hid file")
//              watcher.poll(3, TimeUnit.SECONDS)
//            }
//
//            logger.info("hid file created")
//            watcher.close()
//
//            connectToExisting()
//          }
//        }.start()
//      }
//    }
//
//    connectToHid()
//  }

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

  val LongClickDuration = 500.millis

  val PacketSize = 3
  val FirstByteConstantValue = 4

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

  sealed trait ControllerEvent
  sealed trait ButtonEvent extends ControllerEvent
  sealed trait JoystickEvent extends ControllerEvent with LogicalEvent {
    val asSource = Source.single(this)
  }
  sealed trait JoystickActiveEvent extends JoystickEvent
  case object ButtonA extends ButtonEvent
  case object ButtonB extends ButtonEvent
  case object ButtonC extends ButtonEvent
  case object ButtonD extends ButtonEvent
  case object ButtonHigh extends ButtonEvent
  case object ButtonLow extends ButtonEvent
  case object JoystickUp extends JoystickActiveEvent
  case object JoystickDown extends JoystickActiveEvent
  case object JoystickLeft extends JoystickActiveEvent
  case object JoystickRight extends JoystickActiveEvent
  case object JoystickUpLeft extends JoystickActiveEvent
  case object JoystickDownLeft extends JoystickActiveEvent
  case object JoystickUpRight extends JoystickActiveEvent
  case object JoystickDownRight extends JoystickActiveEvent
  case object Released extends ControllerEvent with JoystickEvent
  case object Unknown extends ControllerEvent

  sealed trait Outcome
  case object Click extends Outcome
  case object LongClick extends Outcome

  sealed trait LogicalEvent
  case class LogicalClick(
    button: ButtonEvent
  ) extends LogicalEvent
  case class LogicalLongClick(
    button: ButtonEvent
  ) extends LogicalEvent

  val Noop = () => ()

  case class ScanState(
    next: ControllerEvent => ScanState,
    out: Source[LogicalEvent, _] = Source.empty
  )

  def decodePhysical(
    bs3: ByteString
  ) :ControllerEvent = {
    val bits = ((bs3(2) & 0xFF) << 8) | (bs3(1) & 0xFF)

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
              Released.asSource
            )
          case j : JoystickEvent =>
            joysticState(j)
          case b : ButtonEvent =>
            buttonState(b)
          case _ =>
            EmptyState
        }
      },
      out = joystickEvent.asSource

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
