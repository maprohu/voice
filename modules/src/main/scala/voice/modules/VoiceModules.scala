package voice.modules

import mvnmod.builder.{RootModuleContainer, ScalaModule}
import mvnmod.modules.MvnmodModules
import toolbox6.modules.{JarTreeModules, Toolbox6Modules, UiModules}
import toolbox8.modules._

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceModules {

  implicit val Root = RootModuleContainer("voice")

  object Modules extends ScalaModule(
    "modules",
    MvnmodModules.Builder,
    Toolbox8Modules.Modules
  )

  object Core extends ScalaModule(
    "core",
    Toolbox6Modules.Macros,
    Toolbox6Modules.Pickling.R3,
    Toolbox8Modules.Leveldb,
    Akka8Modules.Actor,
    Audio,
    Toolbox8Modules.Common,
    mvn.`com.github.wendykierp:JTransforms:jar:3.1`,
    mvn.`com.typesafe.akka:akka-persistence_2.11:jar:2.4.12`
  )

  object Tools extends ScalaModule(
    "tools",
    Core,
    Toolbox8Modules.Leveldb
  )

  object Audio extends ScalaModule(
    "audio",
    Toolbox6Modules.Macros,
    Akka8Modules.Stream,
    mvn.`io.monix:monix_2.11:jar:2.0.6`,
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`
  )

  object Sandbox extends ScalaModule(
    "sandbox",
    mvn.`com.typesafe.akka:akka-stream_2.11:jar:2.4.9`,
    mvn.`com.github.wendykierp:JTransforms:jar:3.1`,
    mvn.`org.scala-lang.modules:scala-swing_2.11:jar:2.0.0-M2`,
    mvn.`io.monix:monix_2.11:jar:2.0.2`,
    mvn.`com.badlogicgames.gdx:gdx-backend-lwjgl:jar:1.9.4`,
    mvn.`com.badlogicgames.gdx:gdx-platform:jar:natives-desktop:1.9.4`,
    mvn.`com.github.yannrichet:JMathPlot:jar:1.0.1`
  )


  object Packaging extends ScalaModule(
    "packaging",
    Toolbox6Modules.Packaging,
    VoiceModules.Modules,
    JarTree8Modules.Client,
    RpiModules.Installer,
    VoiceRpiModules.Home,
    VoiceRpiModules.Mobile,
    Extra8Modules.Hello,
    VoiceRpiModules.Exec,
    mvn.`org.slf4j:slf4j-simple:jar:1.7.21`
  )

  object Testing extends ScalaModule(
    "testing",
    JarTree8Modules.Testing,
    JarTree8Modules.App,
    Modules,
    VoiceRpiModules.Home,
    Tools,
    RpiModules.DBus,
    Audio,
    JarTree8Modules.Client,
    RpiModules.Installer,
    Core,
    VoiceRpiModules.Core,
    Extra8Modules.Hello,
    mvn.`com.jsyn:jsyn:jar:16.7.6`,
    Toolbox6Modules.Logback
  )





}
