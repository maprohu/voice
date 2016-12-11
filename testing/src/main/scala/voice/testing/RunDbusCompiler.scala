package voice.testing

import java.io.ObjectInputStream

import mvnmod.builder.{Module, ModulePath}
import toolbox8.jartree.testing.StreamAppClient
import toolbox8.modules.JarTree8Modules
import voice.environment.Rpis
import voice.modules.{VoiceModules, VoiceRequestModules, VoiceRpiModules}
import voice.requests.compilerpi.{DbusCompilerRequest, DbusCompilerRequest$}

/**
  * Created by maprohu on 08-12-2016.
  */
object RunDbusCompiler {

  val Target = Rpis.Home.tunneled
//  val Target = Rpis.Central.tunneled
  //  val Target = Rpis.Home.wlan
  //  val Target = Rpis.Localhost

  def main(args: Array[String]): Unit = {
    import ammonite.ops._

    voice.builders.build_voice_requests_compilerpi.main(args)

    StreamAppClient
      .requestPlugged(
        VoiceRequestModules.CompileRpi,
        classOf[DbusCompilerRequest].getName,
        { (in, out) =>
          val dis = new ObjectInputStream(in)
          println(
            dis.readObject()
          )
        },
        Target,
        Seq(
//          VoiceModules.Central,
          VoiceRpiModules.Home,
          JarTree8Modules.StreamApp
        )
      )
  }

}
