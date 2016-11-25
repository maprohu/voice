package voice.testing

import voice.core.RpiAudio

/**
  * Created by pappmar on 22/11/2016.
  */
object RunDumpAudioInfo {

  def main(args: Array[String]): Unit = {

    println(
      RpiAudio.dumpInfo
    )

  }

}
