package voice.modules

import mvnmod.builder.{MavenCentralModule, Module, ScalaModule, SubModuleContainer}
import toolbox8.modules._

/**
  * Created by martonpapp on 29/08/16.
  */
object LinuxModules {

  implicit val Root = SubModuleContainer(VoiceModules.Root, "linux")


  object Generator extends ScalaModule(
    "generator",
    VoiceModules.Modules,
    VoiceModules.Environment,
    JarTree8Modules.Testing,
    VoiceRequestModules.Common,
    mvn.`com.nativelibs4java:jnaerator:jar:0.12`
  )

  object Common extends ScalaModule(
    "common",
    mvn.`com.nativelibs4java:jnaerator-runtime:jar:0.12`
  )

  object JnaLib extends ScalaModule(
    "jnalib",
    Common
  )

  object Testing extends ScalaModule(
    "testing",
    JnaLib
  )


}
