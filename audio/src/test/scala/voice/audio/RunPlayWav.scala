package voice.audio

import java.io.File
import javax.sound.sampled.AudioSystem

import scala.io.StdIn

/**
  * Created by maprohu on 05-11-2016.
  */
object RunPlayWav {
  val WavFile = new File("../voice/testing/src/main/resources/boo.wav")
//  val WavFile = new File("../voice/testing/src/main/resources/hello.wav")

  def main(args: Array[String]): Unit = {

    val clip = AudioSystem.getClip
    val is = AudioSystem.getAudioInputStream(WavFile)
    clip.open(is)
    clip.start()


    StdIn.readLine()


  }

}
