package voice.testing

import java.io.File
import javax.sound.sampled.DataLine.Info
import javax.sound.sampled.{AudioSystem, Clip, DataLine, SourceDataLine}

import scala.io.StdIn

/**
  * Created by maprohu on 30-10-2016.
  */
object RunPlayBoo {

  def main(args: Array[String]): Unit = {

    val stream = AudioSystem.getAudioInputStream(
      new File("../voice/testing/src/main/resources/boo.wav")
    )
    val format = stream.getFormat


    val info = new DataLine.Info(classOf[Clip], format)
    val clip = AudioSystem.getLine(info).asInstanceOf[Clip]
    clip.open(stream)
    clip.start()
    clip.drain()
    clip.close()



//    val info = new Info(classOf[SourceDataLine], format)
//    AudioSystem
//      .getMixerInfo
//      .foreach({ m =>
//        println(m)
//        println(m.getName)
//      })
//    AudioSystem
//      .getSourceLineInfo(info)
//      .foreach({ i =>
//        println(i.getClass)
//      })
//
//    val line = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]
//    println(line.getLineInfo)
//    line.start()
//    val buffer = Array.ofDim[Byte](4)
//    Iterator
//      .continually(stream.read(buffer))
//      .takeWhile(_ != -1)
//      .foreach({ c =>
//        println(buffer.toSeq)
//        line.write(buffer, 0, c)
//      })
//
//    line.drain()
//    line.stop()

    StdIn.readLine("boo")

  }

}
