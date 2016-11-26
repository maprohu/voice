package voice.testing

import voice.client.VoiceClient
import voice.common.SshConnectionDetails
import voice.environment.Rpis

/**
  * Created by maprohu on 26-11-2016.
  */
object RunVoiceClient {

  val Target = Rpis.Central

  def main(args: Array[String]): Unit = {
    import Target._
    VoiceClient.run(
      SshConnectionDetails(
        address = host,
        user = user,
        port = sshPort,
        key = key.toString(),
        hostKey = hostKey
      ),
      5000,
      5000
    )
  }

}
