package voice.testing.recording

import java.io.{FileOutputStream, ObjectOutputStream}
import javax.sound.sampled.AudioFormat.Encoding
import javax.sound.sampled.{AudioFormat, AudioSystem}

import com.sun.media.sound.WaveFileWriter
import voice.storage.Syllables

import scala.io.StdIn
import scala.util.Random

/**
  * Created by maprohu on 30-12-2016.
  */
object RunRecordSyllables {

  val SampleRate = 48000
  val RawFileName = "../voice/testing/target/syllables.raw"
  val MetaFileName = "../voice/testing/target/syllables.meta"
  val RecordingFormat = new AudioFormat(
    SampleRate,
    16,
    1,
    true,
    false
  )

  case class Marker(
    position: Long,
    syllable: Syllables.Syllable
  )

  case class Meta(
    markers: Seq[Marker]
  )


  def main(args: Array[String]): Unit = {

    val line = AudioSystem.getTargetDataLine(
      RecordingFormat
    )
    line.open()
    line.start()


    val th = new Thread() {
      override def run(): Unit = {

        val out = new FileOutputStream(RawFileName)
        val buff = Array.ofDim[Byte](4096)

        Iterator
          .continually(
            line.read(buff, 0, buff.length)
          )
          .takeWhile(_ > 0)
          .foreach({ c =>
            out.write(buff, 0, c)
          })

        out.close()
      }
    }
    th.start()

    val syllables =
      Random
        .shuffle(
          Syllables
            .Items
        )
        .toStream

    val input =
      Stream
        .continually(StdIn.readLine())
        .takeWhile(_ != "x")

    val markers =
      syllables
        .zip(input)
        .map(_._1)
        .zipWithIndex
        .map({
          case (s, i) =>
            val pos = line.getLongFramePosition
            println(pos)
            println(s"${i+1}/${syllables.size}: ${s.consonant} ${s.vowel}")
            Marker(pos, s)
        })
        .force

    val meta = Meta(markers)

    println(meta)

    line.stop()
    line.drain()
    line.close()
    th.join()

    val mout = new ObjectOutputStream(
      new FileOutputStream(
        MetaFileName
      )
    )
    mout.writeObject(meta)
    mout.close()


  }

}
