package voice.testing

import akka.actor.{Address, Props, RootActorPath}
import akka.pattern._
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Sink, Source}
import akka.util.Timeout
import monix.eval.Task
import mvnmod.builder.ModulePath
import toolbox8.akka.actor.{ActorSystemTools, ActorTools, SetOut}
import toolbox8.jartree.akka.JarCacheActor.JarKey
import toolbox8.jartree.akka.PluggableServiceActor.PlugRequest
import toolbox8.jartree.akka._
import toolbox8.jartree.app.JarTreeMain
import toolbox8.jartree.client.JarResolver
import toolbox8.jartree.testing.AkkaJartreeClientTools
import toolbox8.modules.JarTree8Modules
import toolbox8.rpi.installer.Rpis
import voice.core.VoiceApi
import voice.core.events.Picklers
import voice.modules.VoiceRpiModules
import voice.rpi.home.VoiceHomePluggable

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.io.StdIn
import monix.execution.FutureUtils.extensions._

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
//      import i.{executionContext => _, _}
//      import monix.execution.Scheduler.Implicits.global
      val out =
        Source
          .actorRef(1000, OverflowStrategy.dropTail)
          .to(Sink.foreach(println))
          .run()

      val fut = for {
        hidActor <-
          actorSystem
            .actorSelection(
              remoteActorSystem / VoiceApi.HidPhysicalActorName
            )
            .resolveOne()
      } yield {
        hidActor ! SetOut(out)
        StdIn.readLine("enter...")
      }

      fut
    }











  }


}
