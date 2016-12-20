package voice.modules

import mvnmod.builder.{ScalaModule, SubModuleContainer}
import toolbox6.modules.Toolbox6Modules
import toolbox8.modules._

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceImplModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "impl")



  object Common extends ScalaModule(
    "common"
  )

  object Threads extends ScalaModule(
    "threads",
    Common,
    mvn.`io.monix:monix-execution_2.11:jar:2.1.2`
  )

  object Testing extends ScalaModule(
    "testing",
    Threads
  )


}
