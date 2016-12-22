package voice.testing

import java.io.ObjectOutputStream

import org.apache.commons.io.IOUtils
import toolbox8.jartree.testing.StreamAppClient
import voice.environment.Rpis
import voice.modules.VoiceRequestModules
import voice.requests.alsa.PlayAlsa
import voice.requests.common.DumpLog

/**
  * Created by maprohu on 08-12-2016.
  */
object RunAlsaPlayback {

//  val Target = Rpis.Home.wlan
//val Target = Rpis.Home.tunneled
//  val Target = Rpis.Mobile.homeCable
  val Target = Rpis.Mobile.tunneled
//  val Target = Rpis.Central.tunneled
  //  val Target = Rpis.Localhost

  def main(args: Array[String]): Unit = {
    println(s"connecting to: ${Target}")

    (1 to 100).foreach { _ =>


      StreamAppClient
        .request(
          VoiceRequestModules.Alsa,
          classOf[PlayAlsa].getName,
          { _ =>

            { (in, out) =>
              val dos = new ObjectOutputStream(out)
              dos.writeObject("null")
              dos.flush()
            }

          },
          Target
        )

    }

  }

}

object RunAlsaPlaybackLocal {
  def main(args: Array[String]): Unit = {

    new PlayAlsa()
      .run("plughw:0,0")
  }
}
