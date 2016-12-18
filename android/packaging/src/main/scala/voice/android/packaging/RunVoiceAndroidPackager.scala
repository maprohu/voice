package voice.android.packaging

import java.io.File

import toolbox8.android.packaging.Jackify
import voice.modules.VoiceAndroidModules

/**
  * Created by maprohu on 18-12-2016.
  */
object RunVoiceAndroidPackager {

  val JackedFile = new File("../voice/android/app/target/jacked.out")

  def main(args: Array[String]): Unit = {

    Jackify
      .jack(
        VoiceAndroidModules.App,
        JackedFile
      )

  }

}
