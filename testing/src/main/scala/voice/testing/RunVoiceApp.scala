package voice.testing

import akka.actor.Props
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Sink, Source}
import toolbox8.akka.actor.GetComponents
import toolbox8.akka.actor.PubSubActor.{Subscribe, Unsubscribe}
import toolbox8.jartree.testing.AkkaJartreeClientTools
import toolbox8.rpi.installer.Rpis
import voice.core.VoiceAppActor.Components
import voice.core.{VoiceApi, VoiceAppActor}
import voice.core.events.Picklers

import scala.io.StdIn

/**
  * Created by maprohu on 12-11-2016.
  */
object RunVoiceApp {

  Picklers.register

  //  val Target = Rpis.Localhost
  val Target = Rpis.Home

  def main(args: Array[String]): Unit = {
    AkkaJartreeClientTools.run(Target) { i =>
      import i._

      val app = actorSystem.actorOf(
        Props(
          classOf[VoiceAppActor]
        )
      )

      val fut = for {
        components <- (app ? GetComponents).mapTo[VoiceAppActor.Components]
        hidActor <- actorSystem
          .actorSelection(
            remoteActorSystem / VoiceApi.HidPhysicalPubSubActorName
          )
          .resolveOne()
      } yield {
        hidActor ! Subscribe(components.logical)
        StdIn.readLine("enter...")
        hidActor ! Unsubscribe(components.logical)
      }

      fut
    }











  }


}
