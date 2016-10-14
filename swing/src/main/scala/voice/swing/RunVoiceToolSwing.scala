package voice.swing

import monix.eval.Task
import monix.execution.{Cancelable, CancelableFuture, FutureUtils}
import rx.Rx
import toolbox6.ui.swing.SwingUI
import voice.tools._
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.duration._
import scala.concurrent.{Future, Promise}

/**
  * Created by pappmar on 14/10/2016.
  */
object RunVoiceToolSwing {

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

  def main(args: Array[String]): Unit = {
    SwingUI.fullScreen { ui =>
      Rx.unsafe {
        new VoiceToolLogic(
          ui,
          binding
        )
      }
    }
  }

}
