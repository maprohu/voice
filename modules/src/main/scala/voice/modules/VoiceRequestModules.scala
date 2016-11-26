package voice.modules

import mvnmod.builder.{ScalaModule, SubModuleContainer}
import toolbox8.modules._

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceRequestModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "requests")


  object Central extends ScalaModule(
    "central",
    VoiceModules.Central
  )





}
