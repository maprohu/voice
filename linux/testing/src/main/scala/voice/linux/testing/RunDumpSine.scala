package voice.linux.testing

import java.nio.{ByteBuffer, ByteOrder, ShortBuffer}
import java.util.{Timer, TimerTask}

import voice.linux.alsa.{AlsaPeriodsAudioConfig, AlsaSampledAudioConfig, MixerSound, Sounds}

/**
  * Created by pappmar on 21/12/2016.
  */
object RunDumpSine {

  def main(args: Array[String]): Unit = {
    val periods = AlsaPeriodsAudioConfig()

    val sine =
      Sounds.repeatInfinitely(
        Sounds.singleWavePeriod(
          440,
          periods.sampled
        ),
        periods
      )

    while (true) {
      println(sine.next)
    }

  }

}

object RunDumpSineFinite {

  val periods = AlsaPeriodsAudioConfig()

  def sine =
    Sounds.repeatFor(
      Sounds.singleWavePeriod(
        440,
        periods.sampled
      ),
      1000,
      periods
    )

  def main(args: Array[String]): Unit = {


    while (true) {
      println(sine.next)
    }

  }

}

object RunDumpMixer {
  def main(args: Array[String]): Unit = {
    import RunDumpSineFinite._


    val mixer = new MixerSound(periods)

    val byteBuffer = ByteBuffer.allocate(periods.bytesPerPeriod)
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    val shortBuffer = byteBuffer.asShortBuffer()

    mixer.buffer = shortBuffer

    val timer = new Timer
    timer.schedule(
      new TimerTask {
        override def run() = {
          mixer.play(sine)
        }
      },
      1000,
      2000
    )

    while (true) {
      shortBuffer.rewind()
      mixer.next

      shortBuffer.rewind()
      while (shortBuffer.hasRemaining) {
        println(shortBuffer.get())
      }
    }


  }
}
