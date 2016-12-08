package voice.testing

import java.io.ObjectInputStream

import toolbox8.jartree.testing.StreamAppClient
import voice.environment.Rpis
import voice.modules.{VoiceModules, VoiceRequestModules, VoiceRpiModules}
import voice.requests.compilerpi.DbusCompiler

/**
  * Created by maprohu on 08-12-2016.
  */
object RunDbusCompiler {

  val Target = Rpis.Home.wlan
  //  val Target = Rpis.Home.wlan
  //  val Target = Rpis.Localhost

  def main(args: Array[String]): Unit = {
    import ammonite.ops._



    StreamAppClient
      .request(
        VoiceRequestModules.CompileRpi,
        classOf[DbusCompiler].getName,
        { _ =>

          { (in, out) =>
            val dis = new ObjectInputStream(in)
            println(
              dis.readObject()
            )
          }
        },
        Target
      )
  }

}
