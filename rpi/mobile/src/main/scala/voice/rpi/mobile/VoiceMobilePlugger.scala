package voice.rpi.mobile

import java.io.{File, FileInputStream}
import java.nio.file.{Files, Path, Paths}

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.{Attributes, Materializer}
import akka.stream.scaladsl.{FileIO, Flow, Sink, Source, StreamConverters}
import akka.util.ByteString
import com.typesafe.scalalogging.{LazyLogging, StrictLogging}
import toolbox6.jartree.api.{JarPlugResponse, JarPlugger}
import toolbox6.jartree.util.JarTreeTools
import toolbox8.jartree.standaloneapi.{JarTreeStandaloneContext, PeerInfo, Service}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import monix.execution.Scheduler.Implicits.global
import voice.rpi.core.{VoiceHid, VoiceParser}


object VoiceMobile {
  val Dir = new File("/opt/voicer")

}
class VoiceMobile(implicit
  actorSystem: ActorSystem,
  materializer: Materializer
) extends Service with StrictLogging {

  val voiceHid = new VoiceHid()

  val parser = new VoiceParser(
    VoiceMobile.Dir,
    actorSystem.scheduler
  )

  val complete =
    voiceHid
      .input
      .via(
        parser.Parser
      )
      .runWith(
        Sink.foreach({ e =>
          logger.info("input: {}", e)
        })
      )


  override def apply(info: PeerInfo): Future[Flow[ByteString, ByteString, _]] = {
    Future.successful(
      Flow.fromSinkAndSource(
        Sink.ignore,
        Source.maybe
      )
    )
  }

  override def close(): Unit = {
    logger.info("stopping voice hid")
    voiceHid.stop()

    logger.info("waiting for shutdown")

    Await.ready(
      complete,
      15.seconds
    )

    logger.info("shutown completed")
  }
}

class VoiceMobilePlugger
  extends JarPlugger[Service, JarTreeStandaloneContext]
  with LazyLogging {

  override def pullAsync(previous: Service, context: JarTreeStandaloneContext): Future[JarPlugResponse[Service]] = {
    import context._
    Future.successful(
      JarTreeTools.andThenResponse(
        new VoiceMobile,
        () => previous.close()
      )
    )
  }
}

