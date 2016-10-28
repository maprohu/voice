package voice.packaging

import toolbox6.jartree.packaging.JarTreePackaging.RunHierarchy
import toolbox8.jartree.client.JarTreeStandaloneClient
import toolbox8.modules.JarTree8Modules
import toolbox8.rpi.installer.Rpis
import voice.modules.VoiceModules
import voice.standalone.VoicePlugger

/**
  * Created by martonpapp on 16/10/16.
  */
object RunUpdateVoiceStandalone {

  def main(args: Array[String]): Unit = {
    JarTreeStandaloneClient.runPlug(
      Rpis.Home.host,
      runHierarchy = RunHierarchy(
        VoiceModules.Standalone,
        runClassName = classOf[VoicePlugger].getName
      ),
      target = JarTree8Modules.Standalone
    )
  }

}
