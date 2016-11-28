package voice.testing

import toolbox8.jartree.request.StreamAppRequest
import voice.api.updateclientinfo.ClientInfo
import voice.client.VoiceClient
import voice.environment.Rpis

/**
  * Created by pappmar on 28/11/2016.
  */
object RunUpdateClientInfo {

//  val Target = Rpis.Localhost
  val Target = Rpis.Central.tunneled

  def main(args: Array[String]): Unit = {

    val ci =
      VoiceClient.createClientInfo(
        Rpis.Localhost,
        "1.2.2.3"
      )

    println(ci)

    StreamAppRequest
      .request(
        ClientInfo.Update,
        ci,
        Target
      )

  }

}
