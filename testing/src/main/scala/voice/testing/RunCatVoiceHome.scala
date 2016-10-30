package voice.testing

import java.io.File

import akka.event.Logging
import akka.stream.Attributes
import akka.stream.scaladsl.{Flow, Sink}
import akka.util.ByteString
import toolbox8.jartree.client.JarTreeStandaloneClient
import toolbox8.rpi.installer.Rpis
import voice.audio.AudioTools
import voice.rpi.core.VoiceHid
import voice.rpi.core.VoiceHid.{ButtonA, ButtonB, LogicalClick}

import scala.concurrent.duration._
import AudioTools.Implicits._

/**
  * Created by martonpapp on 16/10/16.
  */
object RunCatVoiceHome {

  def main(args: Array[String]): Unit = {
    JarTreeStandaloneClient.runCat(
      Rpis.Home.host,
      sink = { mat =>
        import mat._
        Flow[ByteString]
          .via(
            VoiceHid.parser(
              new File("../voice/target/runcat"),
              mat.actorSystem.scheduler
            )
          )
          .to(Sink.ignore)
//          .to(
//            Sink.foreach({ e =>
//              val play = e match {
//                case LogicalClick(ButtonA) =>
//                  Some(
//                    (
//                      300,
//                      100.millis
//                      )
//                  )
//                case LogicalClick(ButtonB) =>
//                  Some(
//                    (
//                      400,
//                      100.millis
//                    )
//                  )
//                case _ =>
//                  None
//              }
//
//              play
//                .foreach({ case (fq, t) =>
//                    AudioTools
//                      .play(
//                        AudioTools
//                          .sine(
//                            fq,
//                            t
//                          )
//                      )
//                })
//            })
//          )
      }
    )
  }

}