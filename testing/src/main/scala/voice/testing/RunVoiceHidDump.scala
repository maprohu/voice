package voice.testing

import akka.actor.{Address, Props, RootActorPath}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Sink, Source}
import toolbox8.jartree.testing.AkkaJartreeClientTools
import toolbox8.rpi.installer.Rpis
import voice.core.VoiceApi
import voice.core.events.Picklers

import scala.io.StdIn
import toolbox8.akka.actor.PubSubActor.{Subscribe, Unsubscribe}

/**
  * Created by maprohu on 05-11-2016.
  */
object RunVoiceHidDump {

  Picklers.register

//  val Target = Rpis.Localhost
  val Target = Rpis.Home

  def main(args: Array[String]): Unit = {
    AkkaJartreeClientTools.run(Target) { i =>
      import i._
      val out =
        Source
          .actorRef(1000, OverflowStrategy.dropTail)
          .to(Sink.foreach(println))
          .run()

      val fut = for {
        hidActor <-
          actorSystem
            .actorSelection(
              remoteActorSystem / VoiceApi.HidPhysicalPubSubActorName
            )
            .resolveOne()
      } yield {
        hidActor ! Subscribe(out)
        StdIn.readLine("enter...")
        hidActor ! Unsubscribe(out)
      }

      fut
    }











  }


}
