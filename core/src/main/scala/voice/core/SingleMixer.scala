package voice.core

import java.util
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import javax.sound.sampled.AudioFormat.Encoding
import javax.sound.sampled._

import com.typesafe.scalalogging.StrictLogging
import toolbox6.logging.LogTools
import voice.core.SingleMixer.Config

import scala.collection.mutable
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * Created by maprohu on 19-11-2016.
  */
object SingleMixer {

  lazy val Global = SingleMixer()

  case class SoundForm(
    seconds: Float,
    form: Float => Float
  )

  object SoundForm {
    def sine(
      seconds: Float,
      frequency: Float,
      amplitude: Float
    ) = {
      SoundForm(
        seconds = seconds,
        form = { t =>
          math.sin(frequency * math.Pi * 2 * t).toFloat * amplitude
        }
      )
    }

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

    def sampledChunk(
      chunk: Array[Byte],
      f: AudioFormat
    ) = {
      require(f.getEncoding == Encoding.PCM_SIGNED)
      require(f.getSampleSizeInBits % 8 == 0)
      require(!f.isBigEndian)
      val channels = f.getChannels
      val bytesPerSample = f.getSampleSizeInBits / 8
      val frameSize = channels * bytesPerSample
      require(f.getFrameSize == frameSize)
      val maxSampleValue = ( 1 << (f.getSampleSizeInBits - 1) ).toFloat
      require(f.getSampleRate == f.getFrameRate)

      chunk
        .toSeq
        .grouped(frameSize)
        .map({ frame =>
          val fs =
            frame
              .grouped(bytesPerSample)
              .map({ sample =>
                sample
                  .reverse
                  .map(_.toInt)
                  .reduceLeft({ (a, b) =>
                    (a << 8) | (b & 0xFF)
                  })
              })
              .toIndexedSeq

          fs.sum / maxSampleValue / channels
        })

    }
  }

  trait PlayableSound {
    def frames: Long
    def millisPerFrame: Float
    def play : Future[Long]
    def playComplete(implicit
      executionContext: ExecutionContext
    ) : Future[Unit]
  }

  def playComplete(
    playableSound: PlayableSound
  )(implicit
    executionContext: ExecutionContext,
    scheduler: ScheduledExecutorService
  ) = {
    val promise = Promise[Unit]()
    playableSound
      .play
      .foreach({ delay =>
        scheduler.schedule(
          new Runnable {
            override def run(): Unit = {
              promise.success()
            }
          },
          ((delay + playableSound.frames) * playableSound.millisPerFrame).toLong,
          TimeUnit.MILLISECONDS
        )
      })
    promise.future
  }

  case class Config(
    bytesPerSample : Int = 2,
    samplesPerSecond : Float = 44100,
    sourceLineFinder: AudioFormat => SourceDataLine = RpiAudio.sourceLineFinder
  ) {
    val bitsPerSample = bytesPerSample * 8
    val audioFormat = new AudioFormat(
      samplesPerSecond,
      bitsPerSample,
      1,
      true, // signed
      false // true = big-endian, false = little-endian
    )
  }

  def apply(
    config: Config = Config()
  ): SingleMixer = new SingleMixer(config)
}

class SingleMixer(
  val config: Config = Config()
) extends StrictLogging with LogTools { mixer =>
  import config._
  import SingleMixer._

  implicit val scheduler = Executors.newSingleThreadScheduledExecutor()

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

  val sdl = sourceLineFinder(audioFormat)
//  sdl.addLineListener(
//    new LineListener {
//      override def update(lineEvent: LineEvent): Unit = println(lineEvent)
//    }
//  )
  sdl.open(audioFormat, bytesPerChunk * 4)
  sdl.start()
//  sdl.write(Array.ofDim[Byte](bytesPerSample), 0, bytesPerSample)

  trait Waiting {
    def start(framesDelay: Long) : Playing
  }

  trait Playing {
    def addTo(data: Array[Float]) : Boolean
  }

  val buffer = new Object {
    val waiting = collection.mutable.Buffer[Waiting]()

  }

  @volatile var stopped = false

  val thread = new Thread() {
    override def run(): Unit = {
      try {
        val chunkFloats = Array.ofDim[Float](samplesPerChunk)
        val chunkBytes = Array.ofDim[Byte](bytesPerChunk)
        val playing = new util.ArrayList[Playing]()

        var framesWritten : Long = 0


        def processWaiting() = {
          buffer
            .waiting
            .foreach({ w =>
              playing add w.start(
                framesWritten - sdl.getLongFramePosition
              )
            })

          buffer.waiting.clear()
        }


        while (!stopped) {
          buffer.synchronized {
            if (!buffer.waiting.isEmpty) {
              processWaiting()
            }
            if (playing.isEmpty) {
              while (!stopped && buffer.waiting.isEmpty) {
                buffer.wait()
              }
              processWaiting()
            }
          }

          if (!stopped) {
            while (sdl.getLongFramePosition < framesWritten - framesWriteAhead) {
              Thread.sleep(writeAheadSleepMillis)
            }

            util.Arrays.fill(chunkFloats, 0)
            val it = playing.iterator

            while (it.hasNext) {
              val p = it.next()
              val hasMore = p.addTo(chunkFloats)
              if (!hasMore) it.remove()
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
      } catch {
        case ex : InterruptedException =>
          if (!stopped) {
            logger.error("interrupted", ex)
            throw ex
          }
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

    sampled(samples, samples.length)

  }

  def sampled(
    samples: Seq[Float]
  ) : PlayableSound = {
    sampled(
      samples,
      samples.length
    )
  }

  def sampled(
    samples: Iterable[Float],
    sampleCount: Long
  ) : PlayableSound = {

    new PlayableSound {
      override val frames: Long = sampleCount
      override def play: Future[Long] = {
        val promise = Promise[Long]()
        buffer.synchronized {
          buffer.waiting += (
            new Waiting {
              override def start(framesDelay: Long): Playing = {
                promise.success(framesDelay)


                new Playing {
                  val it = samples.iterator
//                  var idx = 0
                  override def addTo(data: Array[Float]): Boolean = {
//                    val limit = math.min(sampleCount, idx + data.length)
                    val limit = data.length
                    var target = 0

                    while (target < limit && it.hasNext) {
                      data.update(target, data(target) + it.next())
//                      idx += 1
                      target += 1
                    }

//                    val hasMore = (idx < sampleCount)

//                    hasMore

                    it.hasNext
                  }
                }

              }
            }

          )
          buffer.notify()
        }
        promise.future
      }

      override val millisPerFrame: Float = mixer.millisPerFrame

      override def playComplete(implicit
        executionContext: ExecutionContext
      ): Future[Unit] = {
        SingleMixer.playComplete(
          this
        )
      }
    }
  }

  def stop() = {
    logger.info("stoppig mixer")
    stopped = true
    logger.info("stopping scheduler")
    scheduler.shutdown()
    scheduler.awaitTermination(5, TimeUnit.SECONDS)
    sdl.stop()
    sdl.close()
    thread.interrupt()
    logger.info("waiting for mixer thread to stop")
    thread.join()
    logger.info("mixer stopped")
  }
}
