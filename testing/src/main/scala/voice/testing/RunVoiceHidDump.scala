package voice.testing

import akka.actor.{Address, Props, RootActorPath}
import akka.pattern._
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Sink, Source}
import akka.util.Timeout
import mvnmod.builder.ModulePath
import toolbox8.akka.actor.{ActorSystemTools, ActorTools, SetOut}
import toolbox8.jartree.akka.JarCacheActor.JarKey
import toolbox8.jartree.akka.PluggableServiceActor.PlugRequest
import toolbox8.jartree.akka._
import toolbox8.jartree.app.JarTreeMain
import toolbox8.jartree.client.JarResolver
import toolbox8.modules.JarTree8Modules
import toolbox8.rpi.installer.Rpis
import voice.core.VoiceApi
import voice.modules.VoiceRpiModules
import voice.rpi.home.VoiceHomePluggable

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by maprohu on 05-11-2016.
  */
object RunVoiceHidDump {

//  val Target = Rpis.Localhost
  val Target = Rpis.Home



  def main(args: Array[String]): Unit = {
    JarTreeMain.configureLogging("jartree", true)

    import toolbox8.akka.actor.ActorImplicits._
    implicit val actorSystem = ActorSystemTools.actorSystem(
      "csufomen",
      "192.168.10.122",
      5556
    )
    import actorSystem.dispatcher
    implicit val materializer = ActorMaterializer()

    val remoteActorSystem =
      RootActorPath(
        Address(
          protocol = "akka.tcp",
          system = Target.actorSystemName,
          host = Target.host,
          port = Target.akkaPort
        )
      ) / "user"


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
    } {
      hidActor ! SetOut(out)
    }








  }


}
