package voice.rpi.home

import akka.actor.{Actor, ActorSystem, Props}
import akka.stream.scaladsl.Sink
import toolbox8.jartree.akka.PluggableServiceActor.{PlugContext, Pluggable, Plugged, Previous}
import voice.core.VoiceApi

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import akka.pattern._
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import toolbox8.akka.actor.{ActorSystemTools, DumpActor, PubSubActor, PubSubPublisherActor}
import voice.akka.{HidPhysicalActor, Picklers}

/**
  * Created by maprohu on 06-11-2016.
  */
class VoiceHomePluggable extends Pluggable with LazyLogging {
  override def plug(context: PlugContext): Future[Plugged] = {
    val registration = Picklers.register

    logger.info(s"plugging")
    implicit val actorSystem = context.actorSystem

    val pubSub = actorSystem.actorOf(
      Props(
        classOf[PubSubActor]
      ),
      VoiceApi.HidPhysicalPubSubActorName
    )

    val pubSubInput = actorSystem.actorOf(
      Props(
        classOf[PubSubPublisherActor],
        PubSubPublisherActor.Config(
          target = pubSub
        )
      )
    )

    val feedback = actorSystem.actorOf(
      Props(
        classOf[DumpActor]
      )
    )

    val hidPhysicalActor = actorSystem.actorOf(
      Props(
        classOf[HidPhysicalActor],
        HidPhysicalActor.Config(
          output = pubSubInput,
          feedback = feedback
        )
      ),
      VoiceApi.HidPhysicalActorName
    )


    Future.successful(
      new Plugged {
        override def preUnplug(): Future[Previous] = {
          logger.info(s"unplugging")

          gracefulStop(
            target = hidPhysicalActor,
            timeout = 10.seconds,
            stopMessage = HidPhysicalActor.Stop
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
