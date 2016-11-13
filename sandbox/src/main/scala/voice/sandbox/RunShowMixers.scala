package voice.sandbox

import javax.sound.sampled.AudioSystem

/**
  * Created by maprohu on 13-11-2016.
  */
object RunShowMixers {

  def main(args: Array[String]): Unit = {
    AudioSystem
      .getMixerInfo
      .foreach(println)



  }

}
