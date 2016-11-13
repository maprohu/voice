package voice.core

import java.nio.ByteBuffer
import javax.sound.sampled.AudioSystem

import scala.reflect.io.Streamable

/**
  * Created by maprohu on 13-11-2016.
  */
object RunAnalyzeVowels {

  val Vowels = Seq(
    "a",
    "aa",
    "e",
    "ee",
    "i",
    "o",
    "oe",
    "u",
    "ue"
  )

  def main(args: Array[String]): Unit = {

    Vowels
      .map({ v =>
        val is = AudioSystem.getAudioInputStream(
          getClass.getResource(s"/${v}.wav")
        )

        val bytes = Streamable.bytes(is)
        is.close()


        val sb =
          ByteBuffer
            .wrap(bytes)
            .asShortBuffer()

        val sa = Array.ofDim[Short](sb.remaining())
        sb.get(sa)
        sa
      })
      .map({ b =>
        b.length
      })
      .foreach(println)

  }

}
