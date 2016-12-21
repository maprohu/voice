package voice.linux.alsa

import java.nio.{Buffer, ShortBuffer}
import java.util


trait Sound {
  var buffer : Sounds.Period = null
  def next : Unit
}

class MixerSound(
  config: AlsaPeriodsAudioConfig
) extends Sound {
  private val samplesPerPeriod = config.samplesPerPeriod

  private val sounds = new util.LinkedList[PeriodedSoundData]()

  private val activeSounds = new util.ArrayList[PeriodedSoundData]()

  def play(sound: PeriodedSoundData) = synchronized {
    sounds.add(sound)
  }

  override def next: Unit = {
    activeSounds.clear()

    synchronized {
      val i = sounds.iterator

      while (i.hasNext) {
        val sound = i.next()

        if (sound.ended) {
          i.remove
        } else {
          activeSounds.add(sound)
        }
      }
    }

    var n = 0
    while (n < samplesPerPeriod) {
      var sample = 0

      var si = 0

      while (si < activeSounds.size()) {
        val s = activeSounds.get(si)

        sample += s.next

        si += 1
      }

      if (sample < Short.MinValue) {
        sample = Short.MinValue
      } else if (sample > Short.MaxValue) {
        sample = Short.MaxValue
      }

      buffer.put(sample.toShort)

      n += 1
    }

  }
}

//trait SoundWithLength extends Sound {
//  def length : Long
//
//  def repeatInfinitely : Sound = {
//
//  }
//}
trait SoundWithEnd extends Sound {
  def ended : Boolean
}

trait PeriodedSoundData {
  def ended: Boolean
  def next: Short
}

object Sounds {
  type Period = ShortBuffer
  type SamplesPerSecond = Int
  type SamplesArray = Array[Short]

  def singleWavePeriod(
    requestedFrequency: Double,
    config: AlsaSampledAudioConfig
  ) : SamplesArray = {
    import config._
    val wavePeriodsPerSecond = requestedFrequency

    val samplesPerWavePeriod = (samplesPerSecond / wavePeriodsPerSecond).toInt
    println(samplesPerWavePeriod)

    (0 until samplesPerWavePeriod)
      .map({ x =>
        ( Math.sin( 2 * Math.PI * x / samplesPerWavePeriod ) * (Short.MaxValue / 2 - 1) ).toShort
      })
      .toArray
  }

  def repeatInfinitely(
    data: SamplesArray,
    config: AlsaPeriodsAudioConfig
  ) : PeriodedSoundData = {
    val buffer = ShortBuffer.wrap(data)

    new PeriodedSoundData {
      override def next = {
        val s = buffer.get()

        if (!buffer.hasRemaining) {
          buffer.rewind()
        }

        s
      }
      override def ended = false
    }
  }

  def repeatFor(
    data: SamplesArray,
    millis: Int,
    config: AlsaPeriodsAudioConfig
  ) : PeriodedSoundData = {
    val buffer = ShortBuffer.wrap(data)

    val samplesTotal = config.sampled.samplesPerSecond * millis / 1000

    var position = 0

    new PeriodedSoundData {
      override def next = {
        if (ended) {
          0
        } else {
          val s = buffer.get()

          if (!buffer.hasRemaining) {
            buffer.rewind()
          }
          position += 1

          if (position >= samplesTotal) ended = true

          s
        }
      }

      var ended = position >= samplesTotal
    }
  }

}


