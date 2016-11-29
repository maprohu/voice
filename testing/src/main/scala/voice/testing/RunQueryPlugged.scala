package voice.testing

import toolbox8.jartree.testing.StreamAppClient
import voice.environment.Rpis
import voice.modules.VoiceRequestModules
import voice.requests.common.QueryPlugged

/**
  * Created by maprohu on 29-11-2016.
  */
object RunQueryPlugged {

//  val Target = Rpis.Home.tunneled
  val Target = Rpis.Central.tunneled

  def main(args: Array[String]): Unit = {

    val str : String = StreamAppClient
      .request(
        VoiceRequestModules.Common,
        classOf[QueryPlugged].getName,
        (),
        Target
      )

    println(str)

  }

}
