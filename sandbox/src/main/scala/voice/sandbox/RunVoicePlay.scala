package voice.sandbox

import java.io.FileInputStream
import javax.sound.sampled.AudioSystem

/**
  * Created by martonpapp on 05/09/16.
  */
object RunVoicePlay {
  import RunVoiceRecord._
  import AudioTools._

  def main(args: Array[String]): Unit = {
    val in = new FileInputStream(File)

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
  }

}
