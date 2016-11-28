package voice.modules

import mvnmod.builder.{ScalaModule, SubModuleContainer}
import toolbox8.modules._

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceApiModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "api")


  object UpdateClientInfo extends ScalaModule(
    "updateclientinfo"
  )


}
