package voice.modules

import mvnmod.builder.{MavenCentralModule, ScalaModule, SubModuleContainer}
import toolbox6.modules.{JarTreeModules, Toolbox6Modules, UiModules}
import toolbox8.modules._

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceRpiModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "rpi")

  object Core extends ScalaModule(
    "core",
    VoiceModules.Core,
    VoiceModules.Akka,
    JarTree8Modules.Util,
    VoiceModules.Audio,
    Toolbox8Modules.Common,
    mvn.`io.monix:monix_2.11:jar:2.0.6`
  )

  object Home extends ScalaModule(
    "home",
    Core,
    JarTree8Modules.Akka
  )

  object Mobile extends ScalaModule(
    "mobile",
    VoiceModules.Core,
    JarTree8Modules.StreamApp
  )

  object Exec extends ScalaModule(
    "exec",
    Extra8Modules.Shared
  )






}
