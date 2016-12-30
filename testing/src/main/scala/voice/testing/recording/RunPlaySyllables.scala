package voice.testing.recording

import java.io.{FileInputStream, ObjectInputStream}
import javax.sound.sampled.AudioSystem

/**
  * Created by maprohu on 30-12-2016.
  */
object RunPlaySyllables {
  import RunRecordSyllables._

  def main(args: Array[String]): Unit = {
    val min = new ObjectInputStream(
      new FileInputStream(
        MetaFileName
      )
    )
    val meta = min.readObject().asInstanceOf[Meta]
    min.close()
    println(meta)

    val line = AudioSystem.getSourceDataLine(
      RecordingFormat
    )
    line.open(RecordingFormat, 4096*2)
    line.start()

    val buff = Array.ofDim[Byte](4096)
    val in = new FileInputStream(RawFileName)

    var pos = 0L
    var markers = meta.markers.toList

    Iterator
      .continually(
        in.read(buff)
      )
      .takeWhile(_ > 0)
      .foreach({ c =>
        line.write(buff, 0, c)
        pos += c / 2
        while (markers.headOption.exists(_.position <= pos)) {
          println(markers.head)
          markers = markers.tail
        }
      })

    line.drain()
    line.stop()
    line.close()

  }

}
