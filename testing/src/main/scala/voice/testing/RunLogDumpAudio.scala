package voice.testing

import java.io.{File, ObjectInputStream}

import mvnmod.builder.{MavenTools, ModulePath}
import toolbox8.jartree.testing.StreamAppClient
import toolbox8.modules.JarTree8Modules
import voice.environment.Rpis
import voice.modules.{VoiceModules, VoiceRpiModules}
import voice.rpi.exec.LogDumpAudio

/**
  * Created by pappmar on 25/11/2016.
  */
object RunLogDumpAudio {

//  val Target = Rpis.Localhost
//  val Target = Rpis.Central.tunneled
  val Target = Rpis.Home.tunneled

  def main(args: Array[String]): Unit = {
//    MavenTools
//      .runMavenProject(
//        new File("../voice"),
//        Seq("install")
//      )

    val info : String = StreamAppClient
      .requestPlugged(
        VoiceRpiModules.Exec,
        classOf[LogDumpAudio].getName,
        { (is, os) =>
          val dis = new ObjectInputStream(is)
          dis
            .readObject()
            .asInstanceOf[String]
        },
        Target,
        ModulePath(
          VoiceRpiModules.Home,
          Some(
            ModulePath(
              JarTree8Modules.StreamApp,
              None
            )
          )
        )
      )

    println(info)

  }

}
