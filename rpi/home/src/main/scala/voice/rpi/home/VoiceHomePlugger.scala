package voice.rpi.home

import java.io.FileInputStream
import java.nio.file.{Files, Path, Paths}

import akka.event.Logging
import akka.stream.Attributes
import akka.stream.scaladsl.{FileIO, Flow, Sink, Source, StreamConverters}
import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import toolbox6.jartree.api.{JarPlugResponse, JarPlugger}
import toolbox6.jartree.util.JarTreeTools
import toolbox8.jartree.standaloneapi.{JarTreeStandaloneContext, PeerInfo, Service}

import scala.concurrent.Future

object VoiceHome extends Service {
  override def apply(info: PeerInfo): Future[Flow[ByteString, ByteString, _]] = {
    Future.successful(
      Flow.fromSinkAndSource(
        Sink.ignore,
        Source.maybe
//        StreamConverters
//          .fromInputStream(
//            () => new FileInputStream("/dev/hidraw0")
////            3
//          )
////        FileIO
////          .fromPath(
////            Paths.get("/dev/hidraw0"),
////            3
////          )
//          .log("hid").withAttributes(Attributes.logLevels(onFinish = Logging.InfoLevel, onFailure = Logging.InfoLevel))
////          .log("hid")
      )
    )
  }

  override def close(): Unit = ()
}

class VoiceHomePlugger
  extends JarPlugger[Service, JarTreeStandaloneContext]
  with LazyLogging {

  override def pullAsync(previous: Service, context: JarTreeStandaloneContext): Future[JarPlugResponse[Service]] = {
    Future.successful(
      JarTreeTools.andThenResponse(
        VoiceHome,
        () => previous.close()
      )
    )
  }
}

