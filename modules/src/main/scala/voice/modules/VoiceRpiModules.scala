package voice.modules

import maven.modules.builder.{RootModuleContainer, ScalaModule, SubModuleContainer}
import maven.modules.utils.MavenCentralModule
import repack.modules.RepackModules
import toolbox6.modules.Toolbox6Modules.Logging
import toolbox6.modules.{JarTreeModules, Toolbox6Modules, UiModules}
import toolbox8.modules.{JarTree8Modules, RpiModules, Toolbox8Modules}

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceRpiModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "rpi")

  object Home extends ScalaModule(
    "home",
    JarTree8Modules.Util,
    Toolbox6Modules.Logging,
    JarTreeModules.Util
  )






}
