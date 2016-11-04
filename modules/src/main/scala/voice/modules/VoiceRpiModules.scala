package voice.modules

import maven.modules.builder.{RootModuleContainer, ScalaModule, SubModuleContainer}
import mvnmod.builder.MavenCentralModule
import toolbox6.modules.{JarTreeModules, Toolbox6Modules, UiModules}
import toolbox8.modules.{Extra8Modules, JarTree8Modules, RpiModules, Toolbox8Modules}

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceRpiModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "rpi")

  object Core extends ScalaModule(
    "core",
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
