package voice.core

import java.util
import javax.sound.sampled.{AudioFormat, AudioSystem, LineEvent, LineListener}

import voice.core.SingleMixer.Config

import scala.collection.mutable
import scala.concurrent.{Future, Promise}

/**
  * Created by maprohu on 19-11-2016.
  */
object SingleMixer {

  case class SoundForm(
    seconds: Float,
    form: Float => Float
  )

  object SoundForm {
    def sampled(
      samplesPerSecond: Float,
      samples: IndexedSeq[Float]
    ) : SoundForm = {
      val sampleCount = samples.size
      val secondsPerSample = 1 / samplesPerSecond

      SoundForm(
        seconds = sampleCount * secondsPerSample,
        form = { t =>
          val sampleIndex = t / secondsPerSample

          val i0 = math.min(sampleIndex.floor.toInt, sampleCount-1)
          val i1 = math.min(i0 + 1, sampleCount-1)
          val d = sampleIndex - i0

          ((1 - d) * samples(i0) + d * samples(i1))
        }
      )

    }
  }

  trait PlayableSound {
    def play : Future[Unit]
  }

  case class Config(
    bytesPerSample : Int = 2,
    samplesPerSecond : Float = 44100
  )

  def apply(
    config: Config = Config()
  ): SingleMixer = new SingleMixer(config)
}

class SingleMixer(
  config: Config = Config()
) {
  import config._
  import SingleMixer._

  val bitsPerSample = bytesPerSample * 8
  val secondsPerSample = 1 / samplesPerSecond
  val millisPerFrame = secondsPerSample * 1000
  val samplesPerChunk = 1024 * 4
  val framesPerChunk = samplesPerChunk
  val framesWriteAhead = framesPerChunk
  val writeAheadSleepMillis = (framesWriteAhead * millisPerFrame / 5).toLong
  val bytesPerChunk = samplesPerChunk * bytesPerSample
  val sampleScaleFactor = (1 << (bitsPerSample - 1))
  val maxSampleValue = sampleScaleFactor - 1
  val minSampleValue = -sampleScaleFactor

  val audioFormat = new AudioFormat(
    samplesPerSecond,
    bitsPerSample,
    1,
    true, // signed
    false // true = big-endian, false = little-endian
  )

  val sdl = AudioSystem.getSourceDataLine(audioFormat)
  sdl.addLineListener(
    new LineListener {
      override def update(lineEvent: LineEvent): Unit = println(lineEvent)
    }
  )
  sdl.open()
  sdl.start()
//  sdl.write(Array.ofDim[Byte](bytesPerSample), 0, bytesPerSample)

  trait Playing {
    def addTo(data: Array[Float]) : Boolean
  }

  val buffer = new Object {
    val playing = new util.ArrayList[Playing]()

  }

  val thread = new Thread() {
    override def run(): Unit = {
      val chunkFloats = Array.ofDim[Float](samplesPerChunk)
      val chunkBytes = Array.ofDim[Byte](bytesPerChunk)

      var framesWritten : Long = 0

      while (true) {
        buffer.synchronized {
          while (buffer.playing.isEmpty) {
            buffer.wait()
          }
        }

        println(s"written: ${framesWritten}")
        println(s"pos: ${sdl.getLongFramePosition}")

        while (sdl.getLongFramePosition < framesWritten - framesWriteAhead) {
          Thread.sleep(writeAheadSleepMillis)
        }

        buffer.synchronized {
          util.Arrays.fill(chunkFloats, 0)
          val it = buffer.playing.iterator

          while (it.hasNext) {
            val p = it.next()
            val hasMore = p.addTo(chunkFloats)
            if (!hasMore) it.remove()
          }
        }

        var floatIdx = 0
        var byteIdx = 0

        do {
          var v = (chunkFloats(floatIdx) * sampleScaleFactor).toInt
          if (v < minSampleValue) {
            v = minSampleValue
          } else if (v > maxSampleValue) {
            v = maxSampleValue
          }

          var i = 0
          do {
            chunkBytes.update(byteIdx, (v & 0xFF).toByte)
            v >>= 8

            i += 1
            byteIdx += 1
          } while (i < bytesPerSample)

          floatIdx += 1
        } while (floatIdx < samplesPerChunk)

        sdl.write(chunkBytes, 0, chunkBytes.length)
        framesWritten += framesPerChunk

      }
    }
  }
  thread.start()

  def render(sound: SoundForm) : PlayableSound = {
    val sampleCount = (samplesPerSecond * sound.seconds).toInt

    val samples =
      (0 to sampleCount)
        .map({ idx =>
          val t = idx * secondsPerSample
          sound.form(t)
        })

    new PlayableSound {
      override def play: Future[Unit] = {
        val promise = Promise[Unit]()
        buffer.synchronized {
          buffer.playing.add(
            new Playing {
              var idx = 0
              override def addTo(data: Array[Float]): Boolean = {
                val limit = math.min(sampleCount, idx + data.length)
                var target = 0

                while (idx < limit) {
                  data.update(target, data(target) + samples(idx))
                  idx += 1
                  target += 1
                }

                val hasMore = (idx < sampleCount)

                if (!hasMore) {
                  promise.success()
                }

                hasMore
              }
            }
          )
          buffer.notify()
        }
        promise.future
      }
    }
  }
}
