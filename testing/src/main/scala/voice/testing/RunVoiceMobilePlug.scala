package voice.testing

import toolbox8.jartree.testing.StreamAppPlugger
import toolbox8.rpi.installer.Rpis
import voice.modules.VoiceRpiModules

/**
  * Created by maprohu on 21-11-2016.
  */
object RunVoiceMobilePlug {

  val Target = Rpis.MobileCable

  def main(args: Array[String]): Unit = {
    StreamAppPlugger
      .run(
        VoiceRpiModules.Mobile,
        "voice.rpi.mobile.VoiceMobileRoot",
        Target
      )


  }

}
