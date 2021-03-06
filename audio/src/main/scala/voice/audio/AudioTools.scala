package voice.audio

import java.io.File
import java.net.URLEncoder
import java.nio.file.Files
import javax.sound.sampled._

import akka.stream.Materializer
import akka.stream.scaladsl.Source
import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.atomic.Atomic
import monix.reactive.Consumer
import monix.reactive.OverflowStrategy.Unbounded
import monix.reactive.observers.BufferedSubscriber
import monix.reactive.subjects.{ConcurrentSubject, PublishToOneSubject}

import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.collection.immutable._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.reflect.ClassTag


/**
  * Created by maprohu on 30-10-2016.
  */
object AudioTools {

  type Samples = Iterable[Float]
  type Sound = Source[Float, _]

  val Channels = 2
  val BytesPerChannelPerSample = 2
  val BytesPerSample = 2 * Channels
  val BitsPerChannlePerSample = BytesPerChannelPerSample * 8
  val SamplesPerChannelPerSecond = 44100f
  val SamplesPerSecond = SamplesPerChannelPerSecond * Channels
  val SecondsPerSamplePerChannel = 1f / SamplesPerChannelPerSecond
  val MaxSampleValue = ( 1 << (BitsPerChannlePerSample - 1) ) - 1


  val audioFormat = new AudioFormat(
    SamplesPerChannelPerSecond,
    BitsPerChannlePerSample,
    Channels,
    true, // signed
    true // big-endian
  )

  def play(
    what: Sound
  )(implicit
    materializer: Materializer
  ) = {
    import materializer.executionContext

    val sdl = AudioSystem.getSourceDataLine(audioFormat)
    sdl.addLineListener(
      new LineListener {
        override def update(lineEvent: LineEvent): Unit = println(lineEvent)
      }
    )
    sdl.open()
    sdl.start()
    println(sdl.getBufferSize)
//    val bufferSizeInBytesApprox = BytesPerSample
//    val BufferSizeInBytes = bufferSizeInBytesApprox - (bufferSizeInBytesApprox % BytesPerSample)
//    val BufferSizeInSamples = BufferSizeInBytes / BytesPerSample
    what
      .grouped(1024 * 8)
      .runForeach({ bs =>
        val ba : Array[Byte] =
          bs
            .flatMap({ v =>
              val clip = if (v < -1) -1 else if (v > 1) 1 else v
              val i = (clip * MaxSampleValue).toInt
              Seq(
                ((i >> 8) & 0xFF).toByte,
                (i & 0xFF).toByte
              )
            })(collection.breakOut)

        // blocking
        sdl.write(
          ba,
          0,
          ba.length
        )
      })
      .onComplete({ c =>
        try {
          println(c)
          println("c0")
          sdl.drain() // will block? should dedicate a thread
          println("c1")
          sdl.stop()
          println("c2")
        } catch {
          case ex : Throwable =>
            ex.printStackTrace()
        }
      })

  }

  def sampleCount(
    duration: FiniteDuration
  ) : Int = {
    ((duration.toMillis * SamplesPerSecond) / 1000).toInt
  }

  def forEachChannel(
    size: Int,
    get: () => Float,
    inc: () => Unit
  ) = {
    var ch = 0
    var v : Float = 0

    ArrayTools
      .create[Float](
        size * Channels,
        () => {
          if (ch == 0) {
            v = get()
          }
          v
        },
        () => {
          ch += 1

          if (ch == Channels) {
            ch = 0
            inc()
          }
        }
      )
  }

  def saw(
    periodsPerSecond: Float, // aka. frequency
    duration: FiniteDuration
  ) : Samples = {
    val samplesPerPeriod =
      (SamplesPerChannelPerSecond / periodsPerSecond).toInt

    val period = {
      var x = -2.0f
      val inc = 4.0f / samplesPerPeriod

      forEachChannel(
        samplesPerPeriod,
        () => Math.abs(x) - 1f,
        () => x += inc
      )
        .to[Iterable]
    }

    Stream
      .continually(
        period
      )
      .flatten
      .take(
        sampleCount(duration)
      )
  }

  def square(
    periodsPerSecond: Float, // aka. frequency
    duration: FiniteDuration
  ) : Samples = {
    val samplesPerPeriod =
      (SamplesPerChannelPerSecond / periodsPerSecond).toInt

    val period = {
      var x = -1.0f
      val inc = 2.0f / samplesPerPeriod

      forEachChannel(
        samplesPerPeriod,
        () => Math.signum(x),
        () => x += inc
      )
        .to[Iterable]
    }

    Stream
      .continually(
        period
      )
      .flatten
      .take(
        sampleCount(duration)
      )
  }

  def triangle(
    periodsPerSecond: Float, // aka. frequency
    duration: FiniteDuration
  ) : Samples = {
    val samplesPerPeriod =
      (SamplesPerChannelPerSecond / periodsPerSecond).toInt

    val period = {
      var x = -1.0f
      val inc = 2.0f / samplesPerPeriod

      forEachChannel(
        samplesPerPeriod,
        () => x,
        () => x += inc
      )
        .to[Iterable]
    }

    Stream
      .continually(
        period
      )
      .flatten
      .take(
        sampleCount(duration)
      )
  }

  def sine(
    periodsPerSecond: Float, // aka. frequency
    duration: FiniteDuration
  ) : Samples = {
    val samplesPerPeriod =
      (SamplesPerChannelPerSecond / periodsPerSecond).toInt

    val period = {
      var x = 0.0
      val inc = (2 * Math.PI) / samplesPerPeriod

       forEachChannel(
         samplesPerPeriod,
         () => Math.sin(x).toFloat / 2,
         () => x += inc
       )
       .to[Iterable]
    }

    Stream
      .continually(
        period
      )
      .flatten
      .take(
        sampleCount(duration)
      )
  }



  object Implicits {
    implicit def iterableToSound(iterable: Samples) : Sound = {
      Source(iterable)
    }
  }

  class MixIterator extends Iterator[Float] {
    private var iterators : Seq[Iterator[Float]] = Seq()
    private var live = true

    def add(i: Iterator[Float]) : Boolean = synchronized {
      if (live) {
        iterators :+= i
        true
      } else {
        false
      }
    }

    override def hasNext: Boolean = synchronized {
      if (!live) {
        false
      } else {
        if (iterators.exists(i => !i.hasNext)) {
          iterators = iterators.filter(_.hasNext)
        }

        if (iterators.isEmpty) {
          live = false
          false
        } else {
          true
        }
      }
    }

    override def next(): Float = synchronized {
      iterators.map(_.next()).sum
    }
  }

  class Mixer {

    case class State(
      playing: Seq[Iterator[Float]] = Seq()
    )

    val state = Atomic(State())

    def play(
      samples: Samples
    ) = ???


  }

  def dumpInfo : String = {
    val mixers = AudioSystem.getMixerInfo

    val mixersList =
      mixers
        .map({ mi =>
          val m = AudioSystem.getMixer(mi)
          val s = m.getSourceLineInfo
          val t = m.getTargetLineInfo

          val sourceWithFormats = s
            .map({
              case dl : DataLine.Info =>
                val formats =
                  dl
                    .getFormats
                    .map(f => s"      ${f}")
                    .mkString("\n")

                s"""
                   |    ${dl}
                   |${formats}
                 """.stripMargin

              case dl : Port.Info =>
                s"""
                   |    ${dl}
                 """.stripMargin

            })

          s"""
             |${mi.toString} - ${mi.getName} - ${mi.getDescription}
             |  Source:
             |${sourceWithFormats.mkString("\n")}
             |  Target:
             |${t.map(o => s"    ${o}").mkString("\n")}
             |
           """.stripMargin
        })

    s"""
       |Mixers:
       |${mixers.map(m => s"  ${m}").mkString("\n")}
       |
       |${mixersList.mkString("\n")}
     """.stripMargin
  }

}

object ArrayTools {
  def create[T : ClassTag](
    size: Int,
    get: () => T,
    increment: () => Unit
  ) : Array[T] = {
    val a = Array.ofDim[T](size)

    if (size > 0) {
      var idx = 0
      a(idx) = get()
      idx += 1

      while (idx < size) {
        increment()
        a(idx) = get()
        idx += 1
      }
    }

    a
  }
}

object Talker {
  def play(file: File) = {
    val stream = AudioSystem.getAudioInputStream(
      file
    )
    val format = stream.getFormat
    val info = new DataLine.Info(classOf[Clip], format)
    val clip = AudioSystem.getLine(info).asInstanceOf[Clip]
    val promise = Promise[Unit]()
    clip.addLineListener(
      new LineListener {
        override def update(event: LineEvent): Unit = {
          println(event)
          event.getType match {
            case LineEvent.Type.STOP =>
              promise.trySuccess()
              clip.drain()
              clip.stop()
              clip.close()
              stream.close()
            case _ =>
          }
        }
      }
    )
    clip.open(stream)
    clip.start()

    promise.future
  }
}
class Talker(
  cacheDir: File
)(implicit
  executionContext: ExecutionContext
) {
  cacheDir.mkdirs()

  def error() = {
    cached("error")
  }

  var queue = Future.successful()

  def cached(what: String) = synchronized {
    val file = cache(what)

    val promise = Promise[Unit]()
    val q = queue
    queue = promise.future

    q
      .onComplete({ _ =>
        promise.completeWith(
          Talker.play(
            file
          )
        )
      })

    queue
  }
  def cache(what: String) = synchronized {
    import ammonite.ops._
    import ammonite.ops.ImplicitWd._
    val file = new File(cacheDir, s"${URLEncoder.encode(what, "UTF-8")}.wav")
    if (file.createNewFile()) {
      %%(
        "pico2wave",
        s"--wave=${file.getCanonicalFile.getAbsolutePath}",
        what
      )
    }
    file
  }
}