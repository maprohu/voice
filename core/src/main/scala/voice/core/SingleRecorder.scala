package voice.core

import javax.sound.sampled.{AudioFormat, AudioSystem}

import com.typesafe.scalalogging.StrictLogging
import monix.execution.Cancelable

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}


/**
  * Created by maprohu on 20-11-2016.
  */
class SingleRecorder(
  val config: SingleRecorder.Config = SingleRecorder.Config()
) extends StrictLogging {
  import SingleRecorder._
  import config._

  val bitsPerSample = bytesPerSample * 8

  val samplesPerChunk = 1024 * 1
  val bytesPerChunk = samplesPerChunk * bytesPerSample


  val audioFormat = new AudioFormat(
    samplesPerSecond,
    bitsPerSample,
    1,
    true, // signed
    false // true = big-endian, false = little-endian
  )

  val tdl = AudioSystem.getTargetDataLine(audioFormat)
  tdl.open(audioFormat, bytesPerChunk * 3)

  var chunkStartPos = tdl.getLongFramePosition
  var chunkEndPos = chunkStartPos + samplesPerChunk

  tdl.start()

  val sinks = new Object {
    val items = new java.util.ArrayList[RecordingSink]()

  }

  @volatile var stopped = false

  val thread = new Thread() {
    override def run(): Unit = {
      val bytesChunk = Array.ofDim[Byte](bytesPerChunk)
      var started = true
      while (!stopped) {
        sinks.synchronized {
          if (sinks.items.isEmpty) {
            if (started) {
              tdl.stop()
              tdl.flush()
              chunkStartPos = tdl.getLongFramePosition
              chunkEndPos = chunkStartPos + samplesPerChunk
              started = false
            }
            do {
              sinks.wait()
            } while (sinks.items.isEmpty && !stopped)
          }
        }

        if (!stopped) {

          if (!started) {
            tdl.start()
            started = true
          }

          val readCount = tdl.read(bytesChunk, 0, bytesChunk.length)
          require(readCount == bytesChunk.length)

          sinks.synchronized {
            val it = sinks.items.iterator()

            while (it.hasNext) {
              val p = it.next()
              val wantsMore = p.process(bytesChunk)
              if (!wantsMore) it.remove()
            }
          }

          chunkStartPos = chunkEndPos
          chunkEndPos += samplesPerChunk
        }

      }

    }
  }
  thread.start()

  def record(processor: RecorderProcessor) : Cancelable = {
    val startPos = tdl.getLongFramePosition
    @volatile var endPos = Long.MaxValue

    val finished = Promise[Unit]()

    sinks.synchronized {
      sinks.items.add(
        new RecordingSink {
          override def process(chunk: Array[Byte]): Boolean = {
            val dropLeft = math.max(startPos - chunkStartPos, 0).toInt
            val dropRight = math.max(chunkEndPos - endPos, 0).toInt

            val clipped =
              chunk
                .slice(dropLeft, chunk.length - dropRight)
//                .drop(dropLeft)
//                .drop(dropRight)

            if (clipped.length > 0) {
              processor.process(clipped)
            }

            if (dropRight == 0) {
              true
            } else {
//              println(dropRight)
              finished.success()
              false
            }
          }
        }

      )
      sinks.notify()
    }

    Cancelable { () =>
      endPos = tdl.getLongFramePosition //  + bytesPerChunk * 10

      Await.result(
        finished.future,
        Duration.Inf
      )
    }
  }

  def stop() = {
    logger.info("stoppig recorder")
    stopped = true
    tdl.stop()
    tdl.close()
    thread.interrupt()
    logger.info("waiting for recorder thread to stop")
    thread.join()
    logger.info("recorder stopped")
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

  trait RecorderProcessor {
    def process(chunk: Array[Byte]) : Unit
  }

  def apply(
    config: Config = SingleRecorder.Config()
  ): SingleRecorder = new SingleRecorder(config)
}
