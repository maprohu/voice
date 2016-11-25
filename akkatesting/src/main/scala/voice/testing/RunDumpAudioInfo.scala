package voice.testing

import toolbox8.jartree.logging.LoggingSetup
import voice.audio.AudioTools

/**
  * Created by pappmar on 22/11/2016.
  */
object RunDumpAudioInfo {

  def main(args: Array[String]): Unit = {

    println(
      AudioTools.dumpInfo
    )

  }

}
