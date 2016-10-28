package voice.packaging

import akka.stream.scaladsl.Sink
import toolbox6.jartree.packaging.JarTreePackaging.RunHierarchy
import toolbox8.jartree.client.JarTreeStandaloneClient
import toolbox8.modules.JarTree8Modules
import toolbox8.rpi.installer.Rpis
import voice.modules.VoiceRpiModules
import voice.rpi.home.VoiceHomePlugger

/**
  * Created by martonpapp on 16/10/16.
  */
object RunCatVoiceHome {

  def main(args: Array[String]): Unit = {
    JarTreeStandaloneClient.runCat(
      Rpis.Home.host,
      sink = _ => Sink.foreach(println)
    )
  }

}
