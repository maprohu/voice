package voice.testing

import java.io.File

import mvnmod.builder.MavenTools
import toolbox8.jartree.testing.StreamAppClient
import toolbox8.rpi.installer.Rpis
import voice.modules.VoiceRpiModules

/**
  * Created by maprohu on 21-11-2016.
  */
object RunVoiceMobilePlug {

  val Target = Rpis.MobileCable

  def main(args: Array[String]): Unit = {
    MavenTools
      .runMavenProject(
        new File("../voice"),
        Seq("install")
      )

    StreamAppClient
      .plug(
        VoiceRpiModules.Mobile,
        "voice.rpi.mobile.VoiceMobileRoot",
        Target
      )


  }

}
