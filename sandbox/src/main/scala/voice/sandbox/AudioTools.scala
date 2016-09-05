package voice.sandbox

import java.io.{File, FileInputStream, FileOutputStream}
import javax.sound.sampled.{AudioFormat, AudioSystem}

import akka.util.ByteString

import scala.concurrent.Promise

/**
  * Created by martonpapp on 05/09/16.
  */
object AudioTools {

  val Format = new AudioFormat(44100.0f, 16, 1, true, true)

  def record(file: File) : () => Unit = {
    file.getParentFile.mkdirs()
    val out = new FileOutputStream(file)
    val line = AudioSystem.getTargetDataLine(Format)

    line.open(Format)
    line.start()

    val buffer = Array.ofDim[Byte](line.getBufferSize / 5)

    val finish = Promise[Unit]()

    new Thread() {
      override def run(): Unit = {
        while (!finish.isCompleted) {
          val numRead = line.read(buffer, 0, buffer.length)
          out.write(buffer, 0, numRead)
        }

        out.close()
      }
    }.start()

    { () =>
      finish.success()
    }
  }

  def play(file: File) = {
    new Thread() {
      override def run(): Unit = {
        val in = new FileInputStream(file)

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
      }
    }

  }

}
