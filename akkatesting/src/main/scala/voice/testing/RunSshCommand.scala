package voice.testing

import java.io.File

/**
  * Created by martonpapp on 19/10/16.
  */
object RunSshCommand {

  def main(args: Array[String]): Unit = {
    import toolbox8.installer.SshTools._
    implicit val session = connect

    command("which java")
    scp(new File("../sandbox/pom.xml"), "/tmp/pom.xml")
    command("which java")

    session.disconnect()

  }

}
