package voice.rpi.home

import akka.stream.scaladsl.{FileIO, Flow, Sink, Source, StreamConverters}
import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import toolbox6.jartree.api.{JarCacheLike, JarPlugResponse, JarPlugger, PullParams}
import toolbox6.jartree.impl.JarTree
import toolbox6.jartree.util.JarTreeTools
import toolbox8.jartree.extra.server.ExecServer
import toolbox8.jartree.standaloneapi.{JarTreeStandaloneContext, PeerInfo, Service}

import scala.concurrent.{ExecutionContext, Future}

class VoiceHome(
  cache: JarCacheLike
)(implicit
  executionContext: ExecutionContext
) extends Service with LazyLogging {
  logger.info("created")
  val jarTree = new JarTree(
    classOf[VoiceHome].getClassLoader,
    cache
  )

  override def apply(info: PeerInfo): Future[Flow[ByteString, ByteString, _]] = {
    logger.info("apply")
    Future.successful(
      ExecServer
        .flow(
          this,
          jarTree
        )
    )
  }

  override def close(): Unit = ()
}

class VoiceHomePlugger
  extends JarPlugger[Service, JarTreeStandaloneContext]
  with LazyLogging {

  logger.info(s"created")

  override def pull(
    params: PullParams[Service, JarTreeStandaloneContext]
  ): Future[JarPlugResponse[Service]] = {
    import params._
    logger.info("pulling")
    import context._
    import actorSystem.dispatcher
    Future.successful(
      JarTreeTools.andThenResponse(
        new VoiceHome(context.jarTreeContext.cache),
        () => previous.close()
      )
    )
  }
}

