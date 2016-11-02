package voice.packaging

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
    val module = VoiceModules.Standalone
    val runClassName = classOf[VoicePlugger].getName
    JarTreeStandaloneClient
      .target(
        Rpis.Home.host
      )
      .runPlug(
        module = module,
        runClassName = runClassName,
        target = JarTree8Modules.Standalone
      )
  }

}
