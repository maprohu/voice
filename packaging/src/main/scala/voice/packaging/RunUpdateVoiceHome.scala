package voice.packaging

import java.io.File

import maven.modules.builder.MavenTools
import toolbox8.jartree.client.JarTreeStandaloneClient
import toolbox8.modules.JarTree8Modules
import toolbox8.rpi.installer.Rpis
import voice.modules.{VoiceModules, VoiceRpiModules}
import voice.rpi.home.VoiceHomePlugger
import voice.standalone.VoicePlugger

/**
  * Created by martonpapp on 16/10/16.
  */
object RunUpdateVoiceHome {

  def main(args: Array[String]): Unit = {
    MavenTools.runMavenProject(
      new File("../voice/rpi/home"),
      Seq("install")
    )

    val module =
      VoiceRpiModules.Home
    val runClassName =
      classOf[VoiceHomePlugger].getName

    JarTreeStandaloneClient.runPlug(
      Rpis.Home.host,
      module = module,
      runClassName = runClassName,
      target = JarTree8Modules.Standalone
    )
  }

}
