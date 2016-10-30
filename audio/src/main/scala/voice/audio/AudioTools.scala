package voice.audio

import javax.sound.sampled.{AudioFormat, AudioSystem, LineEvent, LineListener}

import akka.stream.Materializer
import akka.stream.scaladsl.Source
import monix.execution.atomic.Atomic

import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.collection.immutable._
import scala.reflect.ClassTag


/**
  * Created by maprohu on 30-10-2016.
  */
object AudioTools {

  type Samples = Iterable[Float]
  type Sound = Source[Float, _]

  val SampleSizeInBytes = 2
  val SampleSizeInBits = SampleSizeInBytes * 8
  val SamplesPerSecond = 44100
  val SecondsPerSample = 1f / SamplesPerSecond
  val MaxSampleValue = ( 1 << (SampleSizeInBits - 1) ) - 1


  val audioFormat = new AudioFormat(
    SamplesPerSecond.toFloat,
    SampleSizeInBits,
    1, // mono
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
    val bufferSizeInBytesApprox = SampleSizeInBytes
//    val bufferSizeInBytesApprox = sdl.getBufferSize / 5
    val BufferSizeInBytes = bufferSizeInBytesApprox - (bufferSizeInBytesApprox % SampleSizeInBytes)
    val BufferSizeInSamples = BufferSizeInBytes / SampleSizeInBytes
//    val buffer = Array.ofDim[Byte](BufferSize)
    what
      .grouped(BufferSizeInSamples)
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

  def sine(
    periodsPerSecond: Float, // aka. frequency
    duration: FiniteDuration
  ) : Samples = {
    val samplesPerPeriod =
      (SamplesPerSecond / periodsPerSecond).toInt

    val period = {
      var x = 0.0
      val inc = (2 * Math.PI) / samplesPerPeriod

      ArrayTools
        .create[Float](
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