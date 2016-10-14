package voice.tools

import monix.eval.Task
import monix.execution.{CancelableFuture, Scheduler}
import rx.{Rx, Var}
import toolbox6.ui.ast.{Button, Column, UI, Widget}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by pappmar on 14/10/2016.
  */

case class PlayingAudio(
  future: CancelableFuture[Unit]
)
case class RecordedAudio(
  play: Task[PlayingAudio]
)
case class RecordingAudio(
  stop: Task[RecordedAudio]
)
case class Binding(
  record: Task[RecordingAudio]
)

class VoiceToolLogic(
  ui: UI,
  binding: Binding
)(implicit
  ctx: rx.Ctx.Owner,
  executionContext: Scheduler
) {

  case class State(
    record: Option[() => Unit] = None,
    play: Option[() => Unit] = None,
    stop: Option[(String, () => Unit)] = None
  )

  def disable() = state() = State()

  def recorded(d: RecordedAudio) : Unit = {
    ui.run {
      state() = State(
        record = Some(startRecording),
        play = Some({ () =>
          disable()

          d
            .play
            .runAsync
            .foreach({ p =>
              ui.run {
                state() = State(
                  stop = Some(
                    "Playing",
                    { () =>
                      p.future.cancel()
                    }
                  )
                )

                p
                  .future
                  .foreach(_ => recorded(d))
              }
            })
        })
      )
    }
  }

  val startRecording = { () =>
    disable()

    binding
      .record
      .runAsync
      .foreach({ recordingAudio =>

        ui.run {
          state() = State(
            stop = Some(
              (
                "Recording",
                { () =>
                  disable()

                  recordingAudio
                    .stop
                    .runAsync
                    .foreach(d => recorded(d))
                }
              )
            )
          )
        }
      })

  }

  val state : Var[State] = Var[State](State(record = Some(startRecording)))

  val widget : Rx[Widget] = Rx {
    val s = state()

    Column(
      Button(
        "Record",
        ability = s.record.isDefined,
        click = s.record
      ),
      Button(
        "Play",
        ability = s.play.isDefined,
        click = s.play
      ),
      Button(
        label = s.stop.map(o => s"Stop ${o._1}").getOrElse("Stop"),
        ability = s.stop.isDefined,
        click = s.stop.map(_._2)
      )
    )
  }
  widget.foreach(ui.displaySync)




}
