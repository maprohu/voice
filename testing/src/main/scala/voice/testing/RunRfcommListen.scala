package voice.testing

import java.io.ObjectInputStream

import toolbox8.jartree.testing.StreamAppClient
import toolbox8.modules.JarTree8Modules
import voice.environment.Rpis
import voice.modules.{VoiceRequestModules, VoiceRpiModules}
import voice.requests.compilerpi.DbusCompilerRequest
import voice.requests.jnarequests.RfcommListen

/**
  * Created by maprohu on 08-12-2016.
  */
object RunRfcommListen {

  val Target = Rpis.Home.tunneled
//    val Target = Rpis.Home.wlan
  //  val Target = Rpis.Localhost

  def main(args: Array[String]): Unit = {

//    voice.builders.build_voice_requests_compilerpi.main(args)

    StreamAppClient
      .requestPlugged(
        VoiceRequestModules.JnaRequests,
        classOf[RfcommListen].getName,
        { (in, out) =>
        },
        Target,
        Seq(
          VoiceRpiModules.Home,
          JarTree8Modules.StreamApp
        )
      )
  }

}
