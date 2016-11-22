package voice.core

import java.io.{ByteArrayInputStream, DataInputStream}
import java.nio.{ByteBuffer, ByteOrder}
import javax.sound.sampled.AudioFormat.Encoding
import javax.sound.sampled.AudioInputStream

import com.typesafe.scalalogging.LazyLogging
import voice.core.SingleMixer.SoundForm

/**
  * Created by maprohu on 19-11-2016.
  */
object WaveFile extends LazyLogging {

  val FixedSampleSizeInBytes = 2
  val FixedSampleSizeInBits = FixedSampleSizeInBytes * 8
  val maxSampleValue = (1 << (FixedSampleSizeInBits - 1)).toFloat

  def samples(
    ais: AudioInputStream,
    clip : Boolean = true
  ) : IndexedSeq[Float] = {
    val f = ais.getFormat
    require(f.getEncoding == Encoding.PCM_SIGNED)
    require(f.getSampleSizeInBits == FixedSampleSizeInBits)
    require(!f.isBigEndian)
    val channels = f.getChannels
    val bytesPerSample = f.getSampleSizeInBits / 8
    val bytesPerFrame = channels * bytesPerSample
    require(f.getFrameSize == bytesPerFrame)
    require(f.getSampleRate == f.getFrameRate)

    logger.info(s"rate: ${ais.getFormat.getSampleRate}")
    val frameCount = ais.getFrameLength.toInt
    logger.info(s"frames: ${frameCount}")
    val data = Array.ofDim[Byte](
      frameCount * bytesPerFrame
    )

    var ridx = 0

    do {
      ridx += ais.read(data, ridx, data.length-ridx)
    } while (ridx < data.length)
    ais.close()

    samples(
      data,
      channels,
      clip
    )
  }

  def samples(
    data: Array[Byte],
    channels : Int,
    clip: Boolean
  ) : IndexedSeq[Float] = {
    val frameCount = data.length / FixedSampleSizeInBytes / channels

    val dis = ByteBuffer.wrap(data)
    dis.order(ByteOrder.LITTLE_ENDIAN)

    var i = 0
    val floats = Array.ofDim[Float](frameCount)

    var firstNonNull = frameCount
    var lastNonNull = -1

    while (i < frameCount) {
      var ch = 0
      var v = 0
      while (ch < channels) {
        v += dis.getShort
        ch += 1
      }

      if (v != 0) {
        if (lastNonNull == -1) {
          firstNonNull = i
        }
        lastNonNull = i
      }

      floats.update(i, v / maxSampleValue / channels)
      i += 1
    }

    if (clip) {
      logger.info(s"zeros: ${firstNonNull} - ${lastNonNull}")

      val clipped = floats
        .slice(firstNonNull, lastNonNull+1)
        .toIndexedSeq

      logger.info(s"clipped: ${clipped.length}")

      clipped
    } else {
      floats
        .toIndexedSeq
    }

//
//    val framesPerChunk = 1024 * 16
//
//    val buf = Array.ofDim[Byte](bytesPerFrame * framesPerChunk)
//
//    var readCount = 0
//
//    while ((readCount = ais.read(buf)) != -1) {
//      val dis = new DataInputStream(new ByteArrayInputStream(buf))
//
//      while (readCount > 0) {
//        var ch = channels
//
//        while (ch > 0) {
//
//          ch -= 1
//        }
//      }
//
//    }
//
//    val samples =
//      Iterator
//        .continually(ais.read(buf))
//        .takeWhile(_ != -1)
//        .flatMap({ readCount =>
//          buf
//            .toSeq
//            .grouped(bytesPerFrame)
//            .map({ bf =>
//              val fs = bf
//                .grouped(bytesPerSample)
//                .map({ sample =>
//                  sample
//                    .reverse
//                    .map(_.toInt)
//                    .reduceLeft({ (a, b) =>
//                      (a << 8) | (b & 0xFF)
//                    })
//                })
//
//              fs.sum / maxSampleValue / channels
//            })
//
//        })
//        .toIndexedSeq
//
//    if (clip) {
//      samples
//        .dropWhile(_ == 0)
//        .reverse
//        .dropWhile(_ == 0)
//        .reverse
//    } else {
//      samples
//    }
  }


  def toSound(
    ais: AudioInputStream,
    clip : Boolean = true
  ) = {
    SoundForm.sampled(
      ais.getFormat.getSampleRate,
      samples(ais, clip)
    )
  }

}
