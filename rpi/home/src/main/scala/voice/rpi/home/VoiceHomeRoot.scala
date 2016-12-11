package voice.rpi.home

import com.typesafe.scalalogging.StrictLogging
import toolbox6.logging.LogTools
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

object VoiceHomePlugged {
  val DBusPort = 7722
}

class VoiceHomePlugged extends Plugged with StrictLogging with LogTools {

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

  logger.info(s"running dbus socat on port ${VoiceHomePlugged.DBusPort}")
  val dbusSocat =
    new ProcessBuilder()
      .command(
        "sudo",
        "socat",
        s"TCP-LISTEN:${VoiceHomePlugged.DBusPort},reuseaddr,fork",
        "UNIX-CONNECT:/var/run/dbus/system_bus_socket"
      )
      .start()



  override def stop(): Unit = {
    quietly { client.cancel() }
    quietly { dbusSocat.destroy() }
  }

  override def marked[In, Out](marker: RequestMarker[In, Out], in: In): Out = ???
}
