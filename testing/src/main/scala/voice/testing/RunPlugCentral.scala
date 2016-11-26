package voice.testing

import java.io.File

import mvnmod.builder.MavenTools
import toolbox8.jartree.testing.StreamAppClient
import voice.central.CentralRoot
import voice.environment.Rpis
import voice.modules.{VoiceModules, VoiceRpiModules}

/**
  * Created by maprohu on 21-11-2016.
  */
object RunPlugCentral {

  val Target = Rpis.Central.tunneled

  def main(args: Array[String]): Unit = {
    MavenTools
      .runMavenProject(
        new File("../voice"),
        Seq("install")
      )

    StreamAppClient
      .plug(
        VoiceModules.Central,
        classOf[CentralRoot].getName,
        Target
      )


  }

}
