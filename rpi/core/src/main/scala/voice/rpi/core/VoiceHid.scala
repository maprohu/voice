package voice.rpi.core

import java.io.{File, FileInputStream}
import java.nio.file._
import java.util.concurrent.TimeUnit

import akka.actor.Scheduler
import akka.event.Logging
import akka.stream.{Attributes, Materializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source, StreamConverters}
import akka.util.ByteString
import com.typesafe.scalalogging.{LazyLogging, StrictLogging}
import monix.execution.Cancelable
import monix.execution.cancelables.CompositeCancelable
import monix.reactive.Observable
import monix.reactive.OverflowStrategy.Unbounded
import voice.audio.Talker
import voice.rpi.core.VoiceParser.{LogicalClick, LogicalLongClick, ScanState}

import scala.collection.immutable._
import scala.concurrent.{ExecutionContext, ExecutionException, Promise}
import scala.concurrent.duration._
import scala.collection.JavaConversions._

/**
  * Created by maprohu on 29-10-2016.
  */
object VoiceHid {

  val HidFileName = "hidraw0"
  val DevPath = Paths.get("/dev")
  val HidFilePath = DevPath.resolve(HidFileName)

}

class VoiceHid(implicit
  materializer: Materializer
) extends StrictLogging {
  import VoiceHid._
  import materializer.executionContext

  val cancel = CompositeCancelable()

  {
    def connectToHid(): Unit = {
      def connectToExisting() : Unit = {
        StreamConverters
          .fromInputStream(
            () => new FileInputStream(HidFilePath.toFile)
          )
          .mergeMat(
            Source.maybe
          )(Keep.right)
          .mapAsync(1)({ e =>
            queue.offer(e)
          })
          .watchTermination()({ (promise, done) =>
            val c = Cancelable({ () =>
              logger.info("stopping hid")
              promise.trySuccess(None)
            })
            cancel += c
            done.onComplete({ o =>
              logger.info("hid reading complete: {}", o)
              cancel -= c
            })
            done.onFailure({
              case ex =>
                logger.warn("hid failed", ex)
                connectToHid()
            })
          })
          .to(Sink.ignore)
          .run()
      }

      logger.info("creating dev watcher")
      val watcher = DevPath.getFileSystem().newWatchService()
      logger.info("registering dev watcher")
      DevPath.register(
        watcher,
        StandardWatchEventKinds.ENTRY_CREATE
      )
      logger.info("watcher registered")
      if (HidFilePath.toFile.exists()) {
        logger.info(s"${HidFilePath} exists, start reading")
        watcher.close()
        connectToExisting()
      } else {
        logger.info(s"${HidFilePath} does not exists, start watching")
        new Thread() {
          override def run(): Unit = {
            while (!HidFilePath.toFile.exists()) {
              logger.info("polling hid file")
              watcher.poll(3, TimeUnit.SECONDS)
            }

            logger.info("hid file created")
            watcher.close()

            connectToExisting()
          }
        }.start()
      }
    }

    connectToHid()
  }

  val (queue, publisher) =
    Source
      .queue[ByteString](0, OverflowStrategy.backpressure)
      .toMat(
        Sink.asPublisher(true)
      )(Keep.both)
      .run()

  cancel += Cancelable({ () =>
    logger.info("stopping hid queue")
    queue.complete()
  })


  val input = Source.fromPublisher(publisher)

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
  case object ButtonA extends ButtonEvent
  case object ButtonB extends ButtonEvent
  case object ButtonC extends ButtonEvent
  case object ButtonD extends ButtonEvent
  case object ButtonHigh extends ButtonEvent
  case object ButtonLow extends ButtonEvent
  case object JoystickUp extends JoystickEvent
  case object JoystickDown extends JoystickEvent
  case object JoystickLeft extends JoystickEvent
  case object JoystickRight extends JoystickEvent
  case object JoystickUpLeft extends JoystickEvent
  case object JoystickDownLeft extends JoystickEvent
  case object JoystickUpRight extends JoystickEvent
  case object JoystickDownRight extends JoystickEvent
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
        require(bs3(0) == FirstByteConstantValue)
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
