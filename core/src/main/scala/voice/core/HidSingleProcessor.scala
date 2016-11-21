package voice.core

import voice.core.HidPhysicalThread.Processor
import voice.core.events.{ControllerEvent, Unknown}


class HidSingleProcessor(
  processor: Processor
) extends Processor {

  var latest : ControllerEvent = Unknown

  override def onNext(e: ControllerEvent): Unit = {
    if (e != latest) {
      latest = e
      processor.onNext(e)
    }
  }

  override def onError(ex: Throwable): Unit = {
    processor.onError(ex)
  }

  override def onComplete(): Unit = {
    processor.onComplete()
  }
}
