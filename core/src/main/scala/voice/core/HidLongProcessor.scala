package voice.core

import java.util.concurrent.{Executors, ScheduledFuture}

import com.typesafe.scalalogging.StrictLogging
import voice.core.HidPhysicalThread.Processor
import voice.core.ShortLongProcessor.{Wrap, Wrapped}
import voice.core.events.{ControllerEvent, DownEvent, Released}

import scala.concurrent.duration._


object ShortLongProcessor {
  sealed trait Wrap
  sealed trait Click extends Wrap

  case class Down(e: DownEvent) extends Click
  case class Short(e: DownEvent) extends Click
  case class Long(e: DownEvent) extends Click

  case class Error(e: Throwable) extends Wrap
  case object Complete extends Wrap

  type Wrapped = StatefulProcessor[Wrap]
}

trait ShortLongProcessor {
  def onDown(e: DownEvent) : Unit
  def onShort(e: DownEvent) : Unit
  def onLong(e: DownEvent) : Unit
  def onError(ex: Throwable) : Unit
  def onComplete() : Unit
}

class ShortLongWrapper(
  processor: Wrapped
) extends ShortLongProcessor {
  import ShortLongProcessor._
  val runner = new StatefulRunner[Wrap](processor)

  override def onDown(e: DownEvent): Unit = runner.process(Down(e))

  override def onShort(e: DownEvent): Unit = runner.process(Short(e))

  override def onLong(e: DownEvent): Unit = runner.process(Long(e))

  override def onError(ex: Throwable): Unit = runner.process(Error(ex))

  override def onComplete(): Unit = runner.process(Complete)
}

class HidLongProcessor(
  processor: ShortLongProcessor,
  longClickDuration: FiniteDuration = 500.millis
) extends Processor with StrictLogging {

  var isPressed = false
  var latest : DownEvent = null
  var longRunning : ScheduledFuture[_] = null

  val scheduler = Executors.newSingleThreadScheduledExecutor()
  val longRunnable = new Runnable {
    override def run(): Unit = {
      synchronized {
        processor.onLong(latest)
      }
    }
  }

  override def onNext(e: ControllerEvent): Unit = {
    e match {
      case Released =>
        if (isPressed) {
          if (longRunning.cancel(false)) {
            longRunning.synchronized {
              processor.onShort(latest)
            }
          }
        }

        isPressed = false
      case d : DownEvent =>
        if (isPressed) {
          logger.warn(s"pressed (${latest}) while pressed again (${e})")
          longRunning.cancel(false)
        }

        longRunnable.synchronized {
          processor.onDown(d)
          latest = d
        }

        longRunning = scheduler.schedule(
          longRunnable,
          longClickDuration.length,
          longClickDuration.unit
        )

        isPressed = true
      case _ => ???
    }
  }

  override def onError(ex: Throwable): Unit = processor.onError(ex)

  override def onComplete(): Unit = processor.onComplete()
}
