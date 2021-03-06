package voice.testing

import java.io.ObjectOutputStream

import mvnmod.builder.ModulePath
import toolbox8.jartree.testing.StreamAppClient
import voice.environment.Rpis
import voice.modules.{VoiceModules, VoiceRequestModules}
import voice.request.GenerateKey
import voice.requests.central.PutPublicKey

/**
  * Created by maprohu on 26-11-2016.
  */
object RunDeployPublicKey {

  val From = Rpis.Home

  val To = Rpis.Central.tunneled
//  val To = Rpis.Localhost

  def main(args: Array[String]): Unit = {
    val key = RunGenerateKey.run(From)
    val publicKey = key.publicKey.trim
    val clientId = publicKey.split("\\s+").last

    println(s"${clientId} - ${publicKey}")

    StreamAppClient
      .requestPlugged(
        VoiceRequestModules.Central,
        classOf[PutPublicKey].getName,
        { (is, os) =>
          val dos = new ObjectOutputStream(os)
          dos.writeObject(
            PutPublicKey.Input(
              client = clientId,
              publicKey = publicKey
            )
          )
          dos.flush()
        },
        To,
        ModulePath(
          VoiceModules.Central,
          Some(StreamAppClient.StreamAppPath)
        )
      )


  }

}
