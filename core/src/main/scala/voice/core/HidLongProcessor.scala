package voice.core

import java.util.concurrent.{Executors, ScheduledFuture}

import com.typesafe.scalalogging.StrictLogging
import voice.core.HidPhysicalThread.Processor
import voice.core.events.{ControllerEvent, Released}

import scala.concurrent.duration._


trait ShortLongProcessor {
  def onDown(e: ControllerEvent) : Unit
  def onShort(e: ControllerEvent) : Unit
  def onLong(e: ControllerEvent) : Unit
  def onError(ex: Throwable) : Unit
  def onComplete() : Unit
}
class HidLongProcessor(
  processor: ShortLongProcessor,
  longClickDuration: FiniteDuration = 500.millis
) extends Processor with StrictLogging {

  var isPressed = false
  var latest : ControllerEvent = Released
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
    if (e == Released) {
      if (isPressed) {
        if (longRunning.cancel(false)) {
          longRunning.synchronized {
            processor.onShort(latest)
          }
        }
      }

      isPressed = false
    } else {
      if (isPressed) {
        logger.warn(s"pressed (${latest}) while pressed again (${e})")
        longRunning.cancel(false)
      }

      longRunnable.synchronized {
        processor.onDown(e)
        latest = e
      }

      longRunning = scheduler.schedule(
        longRunnable,
        longClickDuration.length,
        longClickDuration.unit
      )

      isPressed = true
    }
  }

  override def onError(ex: Throwable): Unit = processor.onError(ex)

  override def onComplete(): Unit = processor.onComplete()
}
