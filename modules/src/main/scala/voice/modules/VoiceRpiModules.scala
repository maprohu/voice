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
    Akka8Modules.Actor,
    JarTree8Modules.Util,
    Toolbox6Modules.Logging.R1,
    JarTreeModules.Util.R1,
    VoiceModules.Audio,
    Toolbox8Modules.Common,
    mvn.`io.monix:monix_2.11:jar:2.0.5`
  )

  object Home extends ScalaModule(
    "home",
    Core,
    JarTree8Modules.Util,
    Toolbox6Modules.Logging.R1,
    JarTreeModules.Util.R1,
    Extra8Modules.Server,
    JarTreeModules.Impl.R1
  )

  object Mobile extends ScalaModule(
    "mobile",
    Core,
    JarTree8Modules.Util,
    Toolbox6Modules.Logging.R1,
    JarTreeModules.Util.R1
  )

  object Exec extends ScalaModule(
    "exec",
    Extra8Modules.Shared
  )






}
