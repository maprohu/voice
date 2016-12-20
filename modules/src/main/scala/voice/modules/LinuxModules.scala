package voice.modules

import mvnmod.builder.{MavenCentralModule, Module, ScalaModule, SubModuleContainer}
import toolbox6.modules.Toolbox6Modules
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
    Common,
    Toolbox6Modules.Tools
  )

  object Extra extends ScalaModule(
    "extra",
    JnaLib
  )

  object Alsa extends ScalaModule(
    "alsa",
    Extra,
    Toolbox6Modules.Logging.R3,
    mvn.`io.monix:monix-execution_2.11:jar:2.1.2`
  )

  object Testing extends ScalaModule(
    "testing",
    JnaLib,
    mvn.`uk.co.caprica:juds:jar:0.94.1`,
    mvn.`com.kohlschutter.junixsocket:junixsocket-demo:jar:2.0.4`
  )


}
