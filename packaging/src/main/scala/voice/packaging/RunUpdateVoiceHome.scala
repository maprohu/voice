package voice.packaging

import toolbox6.jartree.packaging.JarTreePackaging.RunHierarchy
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
    JarTreeStandaloneClient.runPlug(
      Rpis.Home.host,
      runHierarchy = RunHierarchy(
        VoiceRpiModules.Home,
        runClassName = classOf[VoiceHomePlugger].getName
      ),
      target = JarTree8Modules.Standalone
    )
  }

}
