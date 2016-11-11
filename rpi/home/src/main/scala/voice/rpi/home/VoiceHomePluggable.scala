package voice.rpi.home

import akka.actor.{ActorSystem, Props}
import akka.stream.scaladsl.Sink
import toolbox8.jartree.akka.PluggableServiceActor.{PlugContext, Pluggable, Plugged, Previous}
import voice.core.{HidPhysicalActor, VoiceApi, VoiceHid}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import akka.pattern._
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import toolbox8.akka.actor.ActorSystemTools
import voice.core.events.Picklers

/**
  * Created by maprohu on 06-11-2016.
  */
class VoiceHomePluggable extends Pluggable with LazyLogging {
  override def plug(context: PlugContext): Future[Plugged] = {
    val registration = Picklers.register

    logger.info(s"plugging")
    implicit val actorSystem = context.actorSystem

    val hidPhysicalActor = actorSystem.actorOf(
      Props(
        classOf[HidPhysicalActor]
      ),
      VoiceApi.HidPhysicalActorName
    )

    Future.successful(
      new Plugged {
        override def preUnplug(): Future[Previous] = {
          logger.info(s"unplugging")

          gracefulStop(
            hidPhysicalActor,
            10.seconds
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
