package voice.packaging

import java.io.File

import toolbox6.packaging.{AndroidPackager, MavenTools}
import voice.android.AndroidVoiceMain
import voice.modules.VoiceModules

import scala.io.StdIn
import ammonite.ops._

/**
  * Created by martonpapp on 10/10/16.
  */
object RunPackageVoiceApp {

  val manifest =
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
              package={classOf[AndroidVoiceMain].getPackage.getName}
              android:versionCode="1"
              android:versionName="1.0">
      <application>
        <activity android:name={s".${classOf[AndroidVoiceMain].getSimpleName}"}>
          <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>
        </activity>
      </application>
      <uses-sdk android:minSdkVersion="22" />
    </manifest>

  def main(args: Array[String]): Unit = {
    MavenTools.runMavenProject(
      new File("../voice/android"),
      Seq("install")
    )

    AndroidPackager.run(
      VoiceModules.Android,
      manifest,
      Seq("package", "android:apk")
    )({ f =>
      val to = pwd / up / 'sandbox / 'target / "app.apk"
      mkdir(to / up)
      rm(to)
      cp(Path(f.getAbsolutePath, root) / 'target / "app.apk", to)
    })
  }

}
