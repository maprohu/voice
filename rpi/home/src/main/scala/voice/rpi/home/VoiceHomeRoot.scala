package voice.rpi.home

import toolbox8.jartree.requestapi.RequestMarker
import toolbox8.jartree.streamapp.{PlugParams, Plugged, Root}
import voice.client.VoiceClient
import voice.common.SshConnectionDetails
import voice.environment.{Rpis, Sshs}

import scala.io.{Codec, Source}

/**
  * Created by maprohu on 26-11-2016.
  */
class VoiceHomeRoot extends Root {
  override def plug(params: PlugParams): Plugged = {
    new VoiceHomePlugged
  }
}

class VoiceHomePlugged extends Plugged {
  val central =
    SshConnectionDetails
      .unpickle(
        Source
          .fromURL(
            getClass.getClassLoader.getResource(
              SshConnectionDetails.jsonName(
                Sshs.Central
              )
            )
          )(Codec.UTF8)
          .mkString
      )

  val client =
    VoiceClient
      .run(
        Rpis.Home,
        central.copy(
          user = "voicer",
          port = 22,
          key = "/home/voicer/.ssh/id_rsa"
        )
      )



  override def preUnplug: Any = {
    client.cancel()
  }

  override def postUnplug: Unit = {}

  override def marked[In, Out](marker: RequestMarker[In, Out], in: In): Out = ???
}
