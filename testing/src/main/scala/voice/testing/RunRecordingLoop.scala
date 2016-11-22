package voice.testing

import voice.core.SingleMixer.SoundForm
import voice.core.SingleRecorder.{RecorderProcessor, RecordingSink}
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

//    recorder.record(
//      new RecorderProcessor {
//        override def process(chunk: Array[Byte]): Unit = ()
//      }
//    )

    while (true) {
      StdIn.readLine("press to start recording")

      val data = collection.mutable.Buffer[Array[Byte]]()

      val stopper = recorder.record(
        new RecorderProcessor {
          override def process(chunk: Array[Byte]): Unit = {
            data += chunk.clone()
          }
        }
      )

      StdIn.readLine("press to stop recroding and play\n")

      stopper.cancel()

      val playing = mixer
        .render(
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
        .play

      Await.result(
        playing,
        Duration.Inf
      )



    }


  }

}
