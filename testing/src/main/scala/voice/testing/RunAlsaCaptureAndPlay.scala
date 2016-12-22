package voice.testing

import com.sun.jna.NativeLibrary
import org.apache.commons.io.IOUtils
import toolbox8.jartree.testing.StreamAppClient
import voice.environment.Rpis
import voice.linux.common.asound.AsoundLibrary
import voice.linux.common.c.CommonCLibrary
import voice.modules.VoiceRequestModules
import voice.requests.alsa.{CaptureAlsa, CaptureAndPlayAlsa, PlayAlsa}
import voice.requests.common.DumpLog

/**
  * Created by maprohu on 08-12-2016.
  */
object RunAlsaCaptureAndPlay {

//  val Target = Rpis.Home.wlan
//val Target = Rpis.Home.tunneled
//  val Target = Rpis.Mobile.homeCable
  val Target = Rpis.Mobile.tunneled
//  val Target = Rpis.Central.tunneled
  //  val Target = Rpis.Localhost

  def main(args: Array[String]): Unit = {
    println(s"connecting to: ${Target}")

    StreamAppClient
      .request(
        VoiceRequestModules.Alsa,
        classOf[CaptureAndPlayAlsa].getName,
        { _ =>
          { (in, out) =>
          }
        },
        Target
      )

  }

}

object RunAlsaCaptureAndPlayLocal {
  def main(args: Array[String]): Unit = {

    new CaptureAlsa()
      .run("plughw:0,0")
//      .run("hw:0,0")
  }
}
