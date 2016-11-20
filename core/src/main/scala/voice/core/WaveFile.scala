package voice.core

import java.io.{ByteArrayInputStream, DataInputStream}
import java.nio.{ByteBuffer, ByteOrder}
import javax.sound.sampled.AudioFormat.Encoding
import javax.sound.sampled.AudioInputStream

import akka.util.ByteIterator.ByteArrayIterator
import com.typesafe.scalalogging.LazyLogging
import voice.core.SingleMixer.SoundForm

/**
  * Created by maprohu on 19-11-2016.
  */
object WaveFile extends LazyLogging {

  def samples(
    ais: AudioInputStream,
    clip : Boolean = true
  ) = {
    val f = ais.getFormat
    require(f.getEncoding == Encoding.PCM_SIGNED)
    require(f.getSampleSizeInBits % 8 == 0)
    require(f.getSampleSizeInBits == 16)
    require(!f.isBigEndian)
    val channels = f.getChannels
    val bytesPerSample = f.getSampleSizeInBits / 8
    val bytesPerFrame = channels * bytesPerSample
    require(f.getFrameSize == bytesPerFrame)
    val maxSampleValue = (1 << (f.getSampleSizeInBits - 1)).toFloat
    require(f.getSampleRate == f.getFrameRate)

    logger.info(s"rate: ${ais.getFormat.getSampleRate}")
    val frameCount = ais.getFrameLength.toInt
    logger.info(s"frames: ${frameCount}")
    val data = Array.ofDim[Byte](
      frameCount * bytesPerFrame
    )
    ais.read(data)
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
//      var from = 0
//      while (from < frameCount && floats(from) == 0) {
//        from += 1
//      }
//      var until = frameCount
//      while (until > 0 && floats(until-1) == 0) {
//        until -= 1
//      }

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
