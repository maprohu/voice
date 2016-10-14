package voice.android

import java.io.File

import android.media.MediaPlayer.OnCompletionListener
import android.media.{MediaPlayer, MediaRecorder}
import android.os.Environment
import monix.eval.Task
import monix.execution.{Cancelable, CancelableFuture, FutureUtils, Scheduler}
import voice.tools.{Binding, PlayingAudio, RecordedAudio, RecordingAudio}

import scala.concurrent.Promise
import scala.concurrent.duration._
import scala.util.Try

/**
  * Created by pappmar on 14/10/2016.
  */
class AndroidVoiceTools(implicit
  scheduler: Scheduler
) {
  val RecordingFile =
    new File(
      Environment
        .getExternalStorageDirectory
        .getAbsoluteFile,
      "voicerecording.3gp"
    ).getAbsolutePath

  val binding = Binding(
    record = Task {
      val mediaRecorder = new MediaRecorder()
      mediaRecorder.setAudioSource(JavaBridge.MIC)
      mediaRecorder.setOutputFormat(JavaBridge.F_AMR_WB)
      mediaRecorder.setAudioEncoder(JavaBridge.AMR_WB)
      mediaRecorder.setOutputFile(
        RecordingFile
      )
      mediaRecorder.prepare()
      mediaRecorder.start()

      RecordingAudio(
        stop = Task {
          mediaRecorder.stop()
          mediaRecorder.release()

          RecordedAudio(
            play = Task {
              val mediaPlayer = new MediaPlayer()
              mediaPlayer.setDataSource(RecordingFile)
              mediaPlayer.prepare()
              mediaPlayer.start()

              val promise = Promise[Unit]()

              def stopPlayer() = {
                promise.tryComplete {
                  Try {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                  }
                }
              }

              mediaPlayer.setOnCompletionListener(
                new OnCompletionListener {
                  override def onCompletion(mp: MediaPlayer): Unit = {
                    stopPlayer()
                  }
                }
              )

              PlayingAudio(
                future = CancelableFuture(
                  promise.future,
                  Cancelable(() => stopPlayer())
                )
              )

            }
          )
        }
      )
    }
  )

}
