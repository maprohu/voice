package voice.testing

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.{ActorMaterializer, Attributes}
import akka.stream.scaladsl.{Sink, Source, Tcp}
import akka.util.ByteString
import cx.ath.matthew.utils.Hexdump
import voice.environment.Rpis
import voice.rpi.home.VoiceHomePlugged

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by maprohu on 11-12-2016.
  */
object RunDbusTcpRoot {

  val Target = Rpis.Home.wlan

  val SocatPort = VoiceHomePlugged.DBusPort
  val AkkaPort = 7780
  val UserId = 0


  def main(args: Array[String]): Unit = {
    DBusTcp.run(
      AkkaPort,
      Target.host,
      SocatPort
    )

  }
}

object DBusTcp {
  def stupidlyEncode(data: String): String = {
    Hexdump.toHex(data.getBytes).replaceAll(" ", "")
  }

  def run(
    rootPort: Int,
    targetHost: String,
    targetPort: Int,
    userId: Int = 0,
    logLevel : Logging.LogLevel = Logging.InfoLevel
  ) = {
    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()
    import actorSystem.dispatcher

    println(s"runnig akka at ${rootPort}")
    println(
      Await.ready(
        Tcp()
          .bind(
            "127.0.0.1",
            rootPort
          )
          .to(
            Sink.foreach({ c =>
              Tcp()
                .outgoingConnection(
                  targetHost,
                  targetPort
                )
                .log("server->client", extract = bs => (bs.utf8String, bs))
                .join(
                  c
                    .flow
                    .log("client->server", extract = bs => (bs.utf8String, bs))
                    .prefixAndTail(1)
                    .flatMapConcat({
                      case (Seq(head), tail) =>
                        Source
                          .single(
                            head
                          )
                          .concat(
                            tail
                              .prefixAndTail(1)
                              .flatMapConcat({
                                case (Seq(head2), tail2) =>
                                  Source
                                    .single(
                                      ByteString(s"AUTH EXTERNAL ${stupidlyEncode(s"${userId}")}\r\n")
                                    )
                                    .concat(tail2)
                              })
                          )
                    })
                    .log("sniff->server", extract = bs => (bs.utf8String, bs))
                )
                .withAttributes(Attributes.logLevels(onElement = logLevel))
                .run()
                .onComplete(println)
            })
          )
          .run(),
        Duration.Inf
      )
    )
  }

}


object RunDbusTcp {

  val Target = Rpis.Home.wlan

  val SocatPort = VoiceHomePlugged.DBusPort
  val AkkaPort = 7733
  val UserId = 0

  def stupidlyEncode(data: String): String = {
    Hexdump.toHex(data.getBytes).replaceAll(" ", "")
  }

  def main(args: Array[String]): Unit = {
    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()
    import actorSystem.dispatcher

    println(s"runnig akka at ${AkkaPort}")
    println(
      Await.ready(
        Tcp()
          .bind(
            "127.0.0.1",
            AkkaPort
          )
          .to(
            Sink.foreach({ c =>
              Tcp()
                .outgoingConnection(
                  Target.host,
                  SocatPort
                )
                .log("server->client", extract = bs => (bs.utf8String, bs))
                .join(
                  c
                    .flow
                    .log("client->server", extract = bs => (bs.utf8String, bs))
                )
                .withAttributes(Attributes.logLevels(onElement = Logging.InfoLevel))
                .run()
                .onComplete(println)
            })
          )
          .run(),
        Duration.Inf
      )
    )
  }

}
