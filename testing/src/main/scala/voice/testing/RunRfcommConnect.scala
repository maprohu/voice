package voice.testing

import java.io.ObjectOutputStream

import toolbox8.jartree.testing.StreamAppClient
import toolbox8.modules.JarTree8Modules
import voice.environment.Rpis
import voice.modules.{VoiceRequestModules, VoiceRpiModules}
import voice.requests.jnarequests.{RfcommConnect, RfcommListen}

/**
  * Created by maprohu on 08-12-2016.
  */
object RunRfcommConnect {

//  val Target = Rpis.Home.tunneled
//    val Target = Rpis.Home.wlan
  val Target = Rpis.Mobile.homeCable
  //  val Target = Rpis.Localhost

  def main(args: Array[String]): Unit = {

    voice.builders.build_voice_requests_compilerpi.main(args)

    StreamAppClient
      .requestPlugged(
        VoiceRequestModules.JnaRequests,
        classOf[RfcommConnect].getName,
        { (in, out) =>
          val dos = new ObjectOutputStream(out)
          dos.writeObject(
            RfcommConnect.Input(
              "B8:27:EB:50:3A:5D"
            )
          )
          dos.flush()
        },
        Target,
        Seq(
          VoiceRpiModules.Home,
          JarTree8Modules.StreamApp
        )
      )
  }

}
