package voice.sandbox

import java.io._
import javax.sound.sampled.{AudioFormat, AudioSystem}

import akka.util.ByteString
import monix.reactive.Observable

import scala.concurrent.Promise

/**
  * Created by martonpapp on 05/09/16.
  */
object AudioTools {

  val Format = new AudioFormat(44100.0f, 16, 1, true, true)

  val MixerInfo =
    AudioSystem
      .getMixerInfo
      .find(_.getName.toLowerCase.contains("microphone"))
      .get

  def record(file: File) : () => Unit = {
    file.getParentFile.mkdirs()
    val out = new FileOutputStream(file)
    record(out)
  }

  def record(out: OutputStream) : () => Unit = {
    listen(
      { buffer =>
        { numRead =>
          if (numRead == 0) out.close()
          else out.write(buffer, 0, numRead)
        }
      }
    )
  }

  type Buffer = Array[Byte]

  def listen(
    starter : Buffer => (Int => Unit)
  ) : () => Unit = {
    val line = AudioSystem.getTargetDataLine(Format, MixerInfo)

    line.open(Format)
    line.start()

    val buffer = Array.ofDim[Byte](line.getBufferSize / 5)

    val chunk = starter(buffer)

    new Thread() {
      override def run(): Unit = {
        var numRead = 0
        do {
          numRead = line.read(buffer, 0, buffer.length)
          chunk(numRead)
        } while (numRead != 0)
      }
    }.start()

    val stopper = { () =>
      line.drain()
      line.stop()
      line.close()
    }

    stopper
  }


  def play(file: File) : Unit = {
    val in = new FileInputStream(file)
    play(in)
  }

  val Noop = () => ()

  def play(
    in: InputStream,
    stopper: () => Unit = Noop
  ) : Unit = {
    new Thread() {
      override def run(): Unit = {

        val line = AudioSystem.getSourceDataLine(Format)
        line.open(Format)

        line.start()

        line.getBufferSize

        val buffer = Array.ofDim[Byte](1024 * 16)

        Iterator
          .continually(
            in.read(buffer)
          )
          .takeWhile(_ != -1)
          .foreach({ count =>
            line.write(buffer, 0, count)
          })

        in.close()
        line.drain()
        line.stop()
        line.close()

        stopper()
      }
    }.start()
  }

}
