package voice.testing

import toolbox8.installer.SshTools
import toolbox8.rpi.installer.Rpis.Config

/**
  * Created by martonpapp on 15/10/16.
  */
object RunRpiInstaller {

  val Packages = Seq(
    "oracle-java8-jdk"
  )


  def main(args: Array[String]): Unit = {
    implicit val config = Config(
      host = "192.168.2.6"
    )

    SshTools.run(
//      "apt-cache search jdk"
      s"sudo apt-get -y install ${Packages.mkString(" ")}"
    )
  }

}
