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
    Toolbox6Modules.Macros.R1,
    Toolbox6Modules.Pickling.R1,
    Toolbox8Modules.Leveldb,
    Akka8Modules.Actor
  )

  object Tools extends ScalaModule(
    "tools",
    Core,
    Toolbox8Modules.Leveldb

//    UiModules.Ast,
//    Toolbox6Modules.Macros,
//    mvn.`com.lihaoyi:scalarx_2.11:jar:0.3.1`,
//    mvn.`io.monix:monix-eval_2.11:jar:2.0.4`
  )

  object Audio extends ScalaModule(
    "audio",
    Toolbox6Modules.Macros.R1,
    Akka8Modules.Stream,
    mvn.`io.monix:monix_2.11:jar:2.0.5`,
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.7.8`
  )

//  object Swing extends ScalaModule(
//    "swing",
//    UiModules.Swing,
//    Tools
//  )

//  object Android extends ScalaModule(
//    "android",
//    UiModules.Android,
//    Tools
//  )

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
    VoiceModules.Modules,
//    Android,
    JarTree8Modules.Client,
//    Standalone,
    RpiModules.Installer,
    VoiceRpiModules.Home,
    VoiceRpiModules.Mobile,
    Extra8Modules.Hello,
    VoiceRpiModules.Exec,
    mvn.`org.slf4j:slf4j-simple:jar:1.7.21`
  )

//  object Standalone extends ScalaModule(
//    "standalone",
//    JarTreeModules.Api,
//    JarTree8Modules.StandaloneApi,
//    mvn.`io.monix:monix_2.11:jar:2.0.4`,
//    JarTree8Modules.Util,
//    Toolbox6Modules.Logging
//  )

  object Testing extends ScalaModule(
    "testing",
    Tools,
    RpiModules.DBus,
    Audio,
    JarTree8Modules.Client,
    RpiModules.Installer,
//    Standalone,
    Core,
    VoiceRpiModules.Core,
    Extra8Modules.Hello,
    mvn.`com.typesafe.akka:akka-stream_2.11:jar:2.4.11`,
    mvn.`com.jsyn:jsyn:jar:16.7.6`,
    mvn.`org.slf4j:slf4j-simple:jar:1.7.21`
//    mvn.`org.fusesource.leveldbjni:leveldbjni-all:jar:1.8`
//    mvn.`org.fusesource.leveldbjni:leveldbjni-linux64:jar:1.8`

//    RepackModules.DBus,
//    mvn.`org.hid4java:hid4java:jar:0.4.0`,

//    VoiceModules,
//    JarTree8Modules.Standalone

  )





}
