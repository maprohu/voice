package voice.core

import javax.sound.sampled.AudioFormat.Encoding
import javax.sound.sampled.AudioInputStream

import voice.core.SingleMixer.SoundForm

/**
  * Created by maprohu on 19-11-2016.
  */
object WaveFile {

  def toSound(
    ais: AudioInputStream,
    clip : Boolean = true
  ) = {
    val f = ais.getFormat
    require(f.getEncoding == Encoding.PCM_SIGNED)
    require(f.getSampleSizeInBits % 8 == 0)
    require(!f.isBigEndian)
    val channels = f.getChannels
    val bytesPerSample = f.getSampleSizeInBits / 8
    val frameSize = channels * bytesPerSample
    require(f.getFrameSize == frameSize)
    val maxSampleValue = ( 1 << (f.getSampleSizeInBits - 1) ).toFloat
    require(f.getSampleRate == f.getFrameRate)


    val buf = Array.ofDim[Byte](frameSize)

    val samples =
      Iterator
        .continually(ais.read(buf))
        .takeWhile(_ != -1)
        .map({ _ =>
          val fs =
            buf
              .toSeq
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
        .toIndexedSeq

      val clipped =
        if (clip) {
          samples
            .dropWhile(_ == 0)
            .reverse
            .dropWhile(_ == 0)
            .reverse
        } else {
          samples
        }

    SoundForm.sampled(
      f.getSampleRate,
      clipped
    )

  }

}
