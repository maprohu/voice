package voice.modules

import mvnmod.builder.{ScalaModule, SubModuleContainer}
import toolbox6.modules.Toolbox6Modules
import toolbox8.modules._

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceAndroidModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "android")

  object App extends ScalaModule(
    "app",
    AndroidModules.Runtime
  )

  object Packaging extends ScalaModule(
    "packaging",
    App,
    VoiceModules.Modules,
    AndroidModules.Packaging
  )




}
