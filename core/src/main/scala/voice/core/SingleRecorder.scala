package voice.core

import javax.sound.sampled.{AudioFormat, AudioSystem}


/**
  * Created by maprohu on 20-11-2016.
  */
class SingleRecorder(
  config: SingleRecorder.Config = SingleRecorder.Config()
) {
  import SingleRecorder._
  import config._

  val bitsPerSample = bytesPerSample * 8

  val samplesPerChunk = 1024 * 16
  val bytesPerChunk = samplesPerChunk * bytesPerSample


  val audioFormat = new AudioFormat(
    samplesPerSecond,
    bitsPerSample,
    1,
    true, // signed
    false // true = big-endian, false = little-endian
  )

  val tdl = AudioSystem.getTargetDataLine(audioFormat)
  tdl.open(audioFormat, bytesPerChunk * 4)

  val sinks = new Object {
    val items = new java.util.ArrayList[RecordingSink]()

  }

  val thread = new Thread() {
    override def run(): Unit = {
      val bytesChunk = Array.ofDim[Byte](bytesPerChunk)
      var started = false
      while (true) {
        sinks.synchronized {
          if (sinks.items.isEmpty) {
            if (started) {
              tdl.stop()
              tdl.flush()
              started = false
            }
            do {
              sinks.wait()
            } while (sinks.items.isEmpty)
          }
        }

        if (!started) {
          tdl.start()
          started = true
        }

        tdl.read(bytesChunk, 0, bytesChunk.length)

        sinks.synchronized {
          val it = sinks.items.iterator()

          while (it.hasNext) {
            val p = it.next()
            val wantsMore = p.process(bytesChunk)
            if (!wantsMore) it.remove()
          }
        }
      }

    }
  }
  thread.start()

  def record(sink: RecordingSink) : Unit = {
    sink.synchronized {
      sinks.items.add(sink)
      sink.notify()
    }
  }



}

object SingleRecorder {
  case class Config(
    bytesPerSample : Int = 2,
    samplesPerSecond : Float = 44100
  )

  trait RecordingSink {
    def process(chunk: Array[Byte]) : Boolean
  }

  def apply(
    config: Config = SingleRecorder.Config()
  ): SingleRecorder = new SingleRecorder(config)
}
