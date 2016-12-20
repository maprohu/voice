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
    DBusModules.Lib,
    mvn.`org.zeroturnaround:zt-zip:jar:1.9`,
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`,
    mvn.`org.scala-lang.modules:scala-xml_2.11:jar:1.0.6`
  )

  object JnaRequests extends ScalaModule(
    "jnarequests",
    JarTree8Modules.StreamApp,
    LinuxModules.JnaLib
  )


  object Alsa extends ScalaModule(
    "alsa",
    JarTree8Modules.StreamApp,
    LinuxModules.Alsa
  )



}
