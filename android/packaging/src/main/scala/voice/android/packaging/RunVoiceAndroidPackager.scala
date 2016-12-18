package voice.android.packaging

import toolbox8.android.packaging.Jackify
import voice.modules.VoiceAndroidModules

/**
  * Created by maprohu on 18-12-2016.
  */
object RunVoiceAndroidPackager {

  def main(args: Array[String]): Unit = {
    Jackify
      .jack(
        VoiceAndroidModules.App
      )

  }

}
