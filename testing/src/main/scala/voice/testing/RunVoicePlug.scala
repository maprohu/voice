package voice.testing

import akka.actor.{Address, Props, RootActorPath}
import akka.pattern._
import akka.util.Timeout
import mvnmod.builder.ModulePath
import toolbox8.akka.actor.{ActorSystemTools, ActorTools}
import toolbox8.jartree.akka.JarCacheActor.JarKey
import toolbox8.jartree.akka.PluggableServiceActor.PlugRequest
import toolbox8.jartree.akka._
import toolbox8.jartree.app.JarTreeMain
import toolbox8.jartree.client.JarResolver
import toolbox8.modules.JarTree8Modules
import toolbox8.rpi.installer.Rpis
import voice.modules.VoiceRpiModules
import voice.rpi.home.VoiceHomePluggable

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by maprohu on 05-11-2016.
  */
object RunVoicePlug {

//  val Target = Rpis.Localhost
  val Target = Rpis.Home

  val PluggableModule = VoiceRpiModules.Home
  val PluggableClassName = classOf[VoiceHomePluggable].getName

//  val PluggableModule = JarTree8Modules.Echo
//  val PluggableClassName = classOf[TestPluggable].getName


  def main(args: Array[String]): Unit = {
    JarTreeMain.configureLogging("jartree", true)

    import toolbox8.akka.actor.ActorImplicits._
    implicit val actorSystem = ActorSystemTools.actorSystem(
      "csufomen",
      "192.168.10.122",
      5556
    )
    import actorSystem.dispatcher

    val jars =
      PluggableModule
        .forTarget(
          ModulePath(
            JarTree8Modules.Akka,
            None
          )
        )
        .classPath
        .map({ m =>
          JarResolver.resolveHash(
            JarKey(
              groupId = m.groupId,
              artifactId = m.artifactId,
              version = m.version
            )
          )
        })

    val remoteActorSystem =
      RootActorPath(
        Address(
          protocol = "akka.tcp",
          system = Target.actorSystemName,
          host = Target.host,
          port = Target.akkaPort
        )
      ) / "user"

    val fut = for {
      cacheActor <-
        actorSystem
          .actorSelection(
            remoteActorSystem / JarTreeAkkaApi.JarCacheActorName
          )
          .resolveOne()
      _ <- {
        val uploader =
          actorSystem
            .actorOf(
              Props(
                classOf[JarCacheUploaderActor],
                JarCacheUploaderActor.Config(
                  cache = cacheActor,
                  keys = jars,
                  resources = JarResolver.resources
                )
              )
            )

        ActorTools
          .watchFuture(uploader)
      }
      service <-
        actorSystem
          .actorSelection(
            remoteActorSystem / JarTreeAkkaApi.PluggableServiceActorName
          )
          .resolveOne()
      done <- service.ask(
        PlugRequest(
            classLoader = Some(jars),
            className = PluggableClassName
          )
      )(Timeout(1.minute))
    } yield {
      done
    }




    println(
      Await.result(
        fut,
        Duration.Inf
      )
    )




  }


}
