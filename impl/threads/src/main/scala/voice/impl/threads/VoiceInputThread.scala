package voice.impl.threads

import monix.execution.{Cancelable, CancelableFuture}
import monix.execution.cancelables.BooleanCancelable

/**
  * Created by pappmar on 20/12/2016.
  */
class VoiceInputThread extends Thread with Cancelable {

  @volatile var cancelled = false

  override def run(): Unit = {

    while (!cancelled) {

    }

  }

  override def cancel(): Unit = {
    cancelled = true
  }
}
