package voice.testing

import toolbox6.ssh.SshTools
import voice.environment.Rpis

/**
  * Created by martonpapp on 15/10/16.
  */
object RunRpiInstaller {

  implicit val Target = Rpis.Home

  val Packages = Seq(
    "oracle-java8-jdk"
  )


  def main(args: Array[String]): Unit = {

    SshTools.run(
//      "apt-cache search jdk"
      s"sudo apt-get -y install ${Packages.mkString(" ")}"
    )
  }

}
