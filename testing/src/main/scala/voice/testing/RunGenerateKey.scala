package voice.testing

import java.io.{ObjectInputStream, ObjectOutputStream}

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
    println(
      run(Target)
    )
  }

  def run(target: Rpis.Config) : GenerateKey.Output = {
    StreamAppClient
      .request(
        VoiceModules.Request,
        classOf[GenerateKey].getName,
        { c =>
          { (is, os) =>
            val dos = new ObjectOutputStream(os)
            dos.writeObject(
              GenerateKey.Input(
                keyLocation = s"/home/${target.connectUser}/.ssh/id_rsa"
              )
            )
            dos.flush()

            val dis = new ObjectInputStream(is)
            dis
              .readObject()
              .asInstanceOf[GenerateKey.Output]
          }
        },
        target
      )
  }

}
