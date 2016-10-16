package voice.packaging

import toolbox6.jartree.packaging.JarTreePackaging.RunHierarchy
import toolbox8.jartree.client.JarTreeStandaloneClient
import toolbox8.modules.JarTree8Modules
import voice.modules.VoiceModules
import voice.standalone.VoicePlugger

/**
  * Created by martonpapp on 16/10/16.
  */
object RunUpdateVoiceStandalone {

  def main(args: Array[String]): Unit = {
    JarTreeStandaloneClient.run(
      "localhost",
      9978,
      RunHierarchy(
        VoiceModules.Standalone,
        runClassName = classOf[VoicePlugger].getName
      ),
      target = JarTree8Modules.Standalone
    )
  }

}
