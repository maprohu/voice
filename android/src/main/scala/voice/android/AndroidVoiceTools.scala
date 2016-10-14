package voice.android

import monix.eval.Task
import monix.execution.{Cancelable, CancelableFuture, FutureUtils, Scheduler}
import voice.tools.{Binding, PlayingAudio, RecordedAudio, RecordingAudio}

import scala.concurrent.Promise
import scala.concurrent.duration._

/**
  * Created by pappmar on 14/10/2016.
  */
class AndroidVoiceTools(implicit
  scheduler: Scheduler
) {

  val binding = Binding(
    record = Task {
      RecordingAudio(
        stop = Task(
          RecordedAudio(
            play = Task {
              val promise = Promise[Unit]()

              promise
                .tryCompleteWith(
                  FutureUtils.delayedResult(1.seconds)()
                )

              PlayingAudio(
                future = CancelableFuture(
                  promise.future,
                  Cancelable(() => promise.trySuccess())
                )
              )

            }
          )
        )
      )
    }
  )

}
