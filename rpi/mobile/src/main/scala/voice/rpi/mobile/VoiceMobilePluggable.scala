package voice.rpi.mobile

import akka.actor.Props
import akka.pattern._
import com.typesafe.scalalogging.LazyLogging
import toolbox8.akka.actor.{DumpActor, PubSubActor, PubSubPublisherActor}
import toolbox8.jartree.akka.PluggableServiceActor.{PlugContext, Pluggable, Plugged, Previous}
import voice.core.events.Picklers
import voice.core._

import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Created by maprohu on 06-11-2016.
  */
class VoiceMobilePluggable extends Pluggable with LazyLogging {
  override def plug(context: PlugContext): Future[Plugged] = {
    val registration = Picklers.register

    logger.info(s"plugging")
    implicit val actorSystem = context.actorSystem

    val rootActor = actorSystem.actorOf(
      Props(
        classOf[VoiceMobileActor],
        VoiceMobileActor.Config()
      ),
      VoiceApi.VoiceMobileActorName
    )

    Future.successful(
      new Plugged {
        override def preUnplug(): Future[Previous] = {
          logger.info(s"unplugging")

          gracefulStop(
            target = rootActor,
            timeout = 10.seconds
          )
        }

        override def postUnplug(): Future[Any] = {
          registration.unregister()
          super.postUnplug()
        }
      }
    )
  }
}
