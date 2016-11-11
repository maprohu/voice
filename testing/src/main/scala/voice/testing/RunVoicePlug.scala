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
import toolbox8.jartree.testing.AkkaJartreeClientTools
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



  def main(args: Array[String]): Unit = {
    AkkaJartreeClientTools.plug(
      pluggableModule = PluggableModule,
      pluggableClassName = PluggableClassName,
      rpiTarget = Target
    )
  }


}
