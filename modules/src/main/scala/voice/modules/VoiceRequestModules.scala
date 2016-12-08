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
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`,
    mvn.`libdbus-java:dbus:jar:2.8`,
    mvn.`libdbus-java:dbus-bin:jar:2.8`,
    mvn.`libunix-java:unix:jar:0.5`,
    mvn.`libmatthew-debug-java:hexdump:jar:0.2`,
    mvn.`libmatthew-debug-java:debug-enable:jar:1.1`
  )





}
