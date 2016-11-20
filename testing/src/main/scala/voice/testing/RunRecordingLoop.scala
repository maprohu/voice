package voice.testing

import voice.core.SingleMixer.SoundForm
import voice.core.SingleRecorder.RecordingSink
import voice.core.{SingleMixer, SingleRecorder}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}
import scala.io.StdIn

/**
  * Created by maprohu on 20-11-2016.
  */
object RunRecordingLoop {

  def main(args: Array[String]): Unit = {

    val mixer = SingleMixer()
    val recorder = SingleRecorder()


    while (true) {
      StdIn.readLine("press to start recording")

      @volatile var stopped = false

      val promise = Promise[Seq[Array[Byte]]]

      recorder.record(
        new RecordingSink {
          val b = collection.mutable.Buffer[Array[Byte]]()

          override def process(chunk: Array[Byte]): Boolean = {
            b += chunk.clone()
            if (stopped) {
              promise.success(b)
              false
            } else {
              true
            }
          }
        }
      )

      StdIn.readLine("press to stop recroding and play")

      stopped = true

      val data = Await.result(
        promise.future,
        Duration.Inf
      )

      mixer.render(
        SoundForm.sampled(
          recorder.audioFormat.getSampleRate,
          data
            .flatMap({ chunk =>
              SoundForm.sampledChunk(
                chunk,
                recorder.audioFormat
              )
            })
            .toIndexedSeq
        )


      )



    }


  }

}
