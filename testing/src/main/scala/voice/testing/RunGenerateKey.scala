package voice.testing

import toolbox8.jartree.testing.StreamAppClient
import voice.environment.Rpis
import voice.modules.VoiceModules
import voice.request.GenerateKey

/**
  * Created by maprohu on 26-11-2016.
  */
object RunGenerateKey {

  val Target = Rpis.Home

  def main(args: Array[String]): Unit = {

    val output : GenerateKey.Output = StreamAppClient
      .request(
        VoiceModules.Request,
        classOf[GenerateKey].getName,
        GenerateKey.Input(
          keyLocation = s"/home/${Target.serviceUser}/.ssh/id_rsa"
        ),
        Target
      )

    println(output)

  }

}
