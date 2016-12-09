package voice.rpi.home

import toolbox8.jartree.requestapi.RequestMarker
import toolbox8.jartree.streamapp.{PlugParams, Plugged, Root}
import voice.client.VoiceClient
import voice.common.SshConnectionDetails
import voice.environment.Rpis

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
                Rpis.Central.name.toLowerCase
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
          port = 21,
          key = "/home/voicer/.ssh/id_rsa"
        )
      )


  override def stop(): Unit = {
    client.cancel()
  }

  override def marked[In, Out](marker: RequestMarker[In, Out], in: In): Out = ???
}
