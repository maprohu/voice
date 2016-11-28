package voice.testing

import toolbox8.jartree.request.StreamAppRequest
import voice.api.updateclientinfo.ClientInfo
import voice.client.VoiceClient
import voice.environment.Rpis

/**
  * Created by pappmar on 28/11/2016.
  */
object RunReadAllClientInfo {

//  val Target = Rpis.Localhost
  val Target = Rpis.Central.tunneled

  def main(args: Array[String]): Unit = {

    println(
      StreamAppRequest
        .request(
          ClientInfo.ReadAll,
          (),
          Target
        )
        .mkString("\n")
    )

  }

}
