package voice.modules

import mvnmod.builder.{Module, ModulesBase, RootModuleContainer, ScalaModule}
import mvnmod.modules.MvnmodModules
import toolbox6.modules.{JarTreeModules, Toolbox6Modules, UiModules}
import toolbox8.modules._
import toolbox6.modules.Toolbox6Modules.Logging

/**
  * Created by martonpapp on 29/08/16.
  */
object VoiceModules extends ModulesBase(
  "voice",
  MvnmodModules.Builder,
  Toolbox8Modules.Modules
){
  object Modules extends MetaModule {


  }

  object Core extends ScalaModule(
    "core",
//    Toolbox6Modules.Macros,
//    Toolbox6Modules.Pickling.R3,
    Storage,
    Toolbox8Modules.Common,
//    Toolbox8Modules.Leveldb,
//    mvn.`com.github.wendykierp:JTransforms:jar:3.1`,
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`,
    mvn.`de.dfki.mary:voice-cmu-slt-hsmm:jar:5.2`
      .exclude(
        mvn.`org.slf4j:slf4j-log4j12:jar:1.7.21`,
        mvn.`log4j:log4j:jar:1.2.17`
      ),
    mvn.`org.mapdb:mapdb:jar:3.0.2`
  )

//  object Akka extends ScalaModule(
//    "akka",
//    Core,
//    Audio,
//    Akka8Modules.Actor,
//    Toolbox8Modules.Leveldb,
//    mvn.`com.typesafe.akka:akka-persistence_2.11:jar:2.4.12`
//
//
//  )

  object Storage extends ScalaModule(
    "storage"
  )

  object Tools extends ScalaModule(
    "tools",
    Core,
    Toolbox8Modules.Leveldb
  )

//  object Audio extends ScalaModule(
//    "audio",
//    Toolbox6Modules.Macros,
////    Akka8Modules.Stream,
//    mvn.`io.monix:monix_2.11:jar:2.0.6`,
//    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`
//  )

  object Sandbox extends ScalaModule(
    "sandbox",
    Storage,
    mvn.`com.typesafe.akka:akka-stream_2.11:jar:2.4.9`,
    mvn.`com.github.wendykierp:JTransforms:jar:3.1`,
    mvn.`org.scala-lang.modules:scala-swing_2.11:jar:2.0.0-M2`,
    mvn.`io.monix:monix_2.11:jar:2.0.2`,
    mvn.`com.badlogicgames.gdx:gdx-backend-lwjgl:jar:1.9.4`,
    mvn.`com.badlogicgames.gdx:gdx-platform:jar:natives-desktop:1.9.4`,
    mvn.`com.github.yannrichet:JMathPlot:jar:1.0.1`,
    mvn.`org.mapdb:mapdb:jar:3.0.2`,
    mvn.`org.jogamp.jogl:jogl-all-main:jar:2.3.2`,
    mvn.`org.jogamp.gluegen:gluegen-rt-main:jar:2.3.2`,
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`

  )

  object Common extends ScalaModule(
    "common",
    JarTree8Modules.Common,
    mvn.`com.lihaoyi:upickle_2.11:jar:0.4.2`,
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`
  )

  object Environment extends ScalaModule(
    "environment",
    Common,
    Toolbox8Modules.Installer
  )


  object Central extends ScalaModule(
    "central",
    Common,
    VoiceApiModules.UpdateClientInfo,
    JarTree8Modules.StreamApp,
    mvn.`org.mapdb:mapdb:jar:3.0.2`,
    DBusModules.Lib
  )

  object Client extends ScalaModule(
    "client",
    VoiceModules.Modules,
    Common,
    Environment,
    VoiceApiModules.UpdateClientInfo,
    Toolbox6Modules.Ssh,
    Toolbox6Modules.Logging,
    JarTree8Modules.Request,
    mvn.`io.monix:monix-execution_2.11:jar:2.1.1`
  )

  object Request extends ScalaModule(
    "request",
    JarTree8Modules.StreamApp,
    mvn.`com.lihaoyi:ammonite-ops_2.11:jar:0.8.0`
  )

  object Jni extends ScalaModule(
    "jni"
  )

//  object Packaging extends ScalaModule(
//    "packaging",
//    Toolbox6Modules.Packaging,
//    Environment,
//    VoiceModules.Modules,
//    JarTree8Modules.Client,
//    RpiModules.Installer,
////    VoiceRpiModules.Home,
//    VoiceRpiModules.Mobile,
////    Extra8Modules.Hello,
//    VoiceRpiModules.Exec,
//    mvn.`org.slf4j:slf4j-simple:jar:1.7.21`
//  )

  object Testing extends ScalaModule(
    "testing",
//    Akka,
    Builders,
    Environment,
    Jni,
    Central,
    Client,
    Request,
    VoiceRpiModules.Home,
    JarTree8Modules.Testing,
    JarTree8Modules.PluggedNull,
//    JarTree8Modules.App,
    Modules,
//    VoiceRpiModules.Home,
    VoiceRpiModules.Mobile,
    Tools,
//    RpiModules.DBus,
//    Audio,
    JarTree8Modules.Client,
    RpiModules.Installer,
    Core,
    Storage,
//    VoiceRpiModules.Core,
//    Extra8Modules.Hello,
    Toolbox6Modules.Logging,
    Toolbox6Modules.Logback,
    VoiceRpiModules.Exec,
    VoiceRequestModules.Central,
    VoiceRequestModules.Common,
    VoiceRequestModules.CompileRpi,
    VoiceRequestModules.JnaRequests,
    VoiceRequestModules.Alsa,
    mvn.`com.typesafe.akka:akka-stream_2.11:jar:2.4.14`,
    mvn.`com.jssrc:jssrc:jar:1.0.1`,
    mvn.`com.nativelibs4java:jnaerator:jar:0.12`,
    mvn.`com.ibm:couchdb-scala_2.11:jar:0.7.2`
  )






}
