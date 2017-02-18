package voice.modules

import mvnmod.builder.{Scala212Module, ScalaModule, SubModuleContainer}
import toolbox6.modules.Toolbox6Modules
import toolbox8.modules._

/**
  * Created by martonpapp on 29/08/16.
  */
object CGModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "cg")


  object Core extends Scala212Module(
    "core"
  )


}
