package voice.modules

import maven.modules.builder.{RootModuleContainer, ScalaModule}
import maven.modules.utils.MavenCentralModule
import repack.modules.RepackModules
import toolbox6.modules.Toolbox6Modules.Logging
import toolbox6.modules.{JarTreeModules, Toolbox6Modules, UiModules}
import toolbox8.modules.{JarTree8Modules, RpiModules, Toolbox8Modules}

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceModules extends MavenCentralModule(
  "voice-modules",
  "voice-modules",
  "1.0.0-SNAPSHOT"
){

  implicit val Root = RootModuleContainer("voice")

  object Tools extends ScalaModule(
    "tools",
    UiModules.Ast,
    Toolbox6Modules.Macros,
    mvn.`com.lihaoyi:scalarx_2.11:jar:0.3.1`,
    mvn.`io.monix:monix-eval_2.11:jar:2.0.4`
  )

  object Swing extends ScalaModule(
    "swing",
    UiModules.Swing,
    Tools
  )

  object Android extends ScalaModule(
    "android",
    UiModules.Android,
    Tools
  )

  object Sandbox extends ScalaModule(
    "sandbox",
    mvn.`com.typesafe.akka:akka-stream_2.11:jar:2.4.9`,
    mvn.`com.github.wendykierp:JTransforms:jar:3.1`,
    mvn.`org.scala-lang.modules:scala-swing_2.11:jar:2.0.0-M2`,
    mvn.`io.monix:monix_2.11:jar:2.0.2`,
    mvn.`com.badlogicgames.gdx:gdx-backend-lwjgl:jar:1.9.4`,
    mvn.`com.badlogicgames.gdx:gdx-platform:jar:natives-desktop:1.9.4`,
    mvn.`com.github.wendykierp:JTransforms:jar:3.1`,
    mvn.`com.github.yannrichet:JMathPlot:jar:1.0.1`
  )


  object Packaging extends ScalaModule(
    "packaging",
    Toolbox6Modules.Packaging,
    VoiceModules,
    Android,
    JarTree8Modules.Client,
    Standalone,
    mvn.`org.slf4j:slf4j-simple:jar:1.7.21`
  )

  object Standalone extends ScalaModule(
    "standalone",
    JarTreeModules.Api,
    JarTree8Modules.StandaloneApi,
    mvn.`io.monix:monix_2.11:jar:2.0.4`,
    JarTree8Modules.Util,
    Toolbox6Modules.Logging
  )

  object Testing extends ScalaModule(
    "testing",
    RpiModules.DBus,
//    RepackModules.DBus,
    mvn.`org.hid4java:hid4java:jar:0.4.0`

//    VoiceModules,
//    JarTree8Modules.Standalone

  )





}
