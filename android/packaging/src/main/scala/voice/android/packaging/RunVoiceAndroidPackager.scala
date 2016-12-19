package voice.android.packaging

import java.io.File

import toolbox8.android.packaging.{Aptify, Jackify}
import voice.modules.VoiceAndroidModules

/**
  * Created by maprohu on 18-12-2016.
  */
object RunVoiceAndroidPackager {

  // http://czak.pl/2016/05/31/handbuilt-android-project.html

  def main(args: Array[String]): Unit = {
    Aptify
      .run(
        VoiceAndroidModules.App
      )
  }

//  val JackedOutDir = new File("../voice/android/app/target/jackout")
//
//  def main(args: Array[String]): Unit = {
//
//    Jackify
//      .jack(
//        VoiceAndroidModules.App,
//        JackedOutDir
//      )
//
//  }

}
