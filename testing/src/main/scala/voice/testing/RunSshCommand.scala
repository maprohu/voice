package voice.testing

import java.io.File

import voice.environment.Rpis

/**
  * Created by martonpapp on 19/10/16.
  */
object RunSshCommand {

//  implicit val Target = Rpis.MobileCable
  implicit val Target = Rpis.Central

  def main(args: Array[String]): Unit = {
    import toolbox6.ssh.SshTools._
    implicit val session = connect

    command("which java")
//    scp(new File("../sandbox/pom.xml"), "/tmp/pom.xml")
//    command("which java")

    session.disconnect()

  }

}
