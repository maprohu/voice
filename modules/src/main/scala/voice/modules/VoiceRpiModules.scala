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
//    JarTree8Modules.Util,
    VoiceModules.Audio,
    Toolbox8Modules.Common,
    mvn.`io.monix:monix_2.11:jar:2.0.6`
  )

//  object Home extends ScalaModule(
//    "home",
//    Core,
//    JarTree8Modules.Akka
//  )

  object Mobile extends ScalaModule(
    "mobile",
    VoiceModules.Core,
    JarTree8Modules.StreamApp,
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`
  )

  object Exec extends ScalaModule(
    "exec",
    JarTree8Modules.StreamApp,
    VoiceModules.Core
  )






}
