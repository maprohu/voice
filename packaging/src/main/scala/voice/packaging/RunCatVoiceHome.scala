package voice.packaging

import akka.event.Logging
import akka.stream.{Attributes, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Sink, StreamConverters}
import akka.util.ByteString
import toolbox8.jartree.client.JarTreeStandaloneClient
import toolbox8.rpi.installer.Rpis
import voice.rpi.core.VoiceHid

/**
  * Created by martonpapp on 16/10/16.
  */
object RunCatVoiceHome {

  def main(args: Array[String]): Unit = {
    JarTreeStandaloneClient.runCat(
      Rpis.Home.host,
      sink = { mat =>
        Flow[ByteString]
          .log("hid").withAttributes(Attributes.logLevels(onElement = Logging.DebugLevel))
          .via(
            VoiceHid
              .parser(mat.actorSystem.scheduler, mat.actorSystem.dispatcher)
              .log("button").withAttributes(Attributes.logLevels(onElement = Logging.InfoLevel))
          )
          .to(Sink.ignore)
      }
    )
  }

}
