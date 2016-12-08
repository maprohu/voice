package voice.modules

import mvnmod.builder.{ScalaModule, SubModuleContainer}
import toolbox8.modules._

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceRequestModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "requests")


  object Common extends ScalaModule(
    "common",
    JarTree8Modules.StreamApp
  )

  object Central extends ScalaModule(
    "central",
    VoiceModules.Central
  )

  object CompileRpi extends ScalaModule(
    "compilerpi",
    JarTree8Modules.StreamApp,
    mvn.`org.zeroturnaround:zt-zip:jar:1.9`,
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`
  )





}
