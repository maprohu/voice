package voice.testing

import java.net.InetAddress

import toolbox6.ssh.SshTools
import voice.environment.Rpis

import scala.io.Source

/**
  * Created by pappmar on 28/11/2016.
  */
object RunSshClientAddress {

  implicit val Target = Rpis.Central.remote

  def main(args: Array[String]): Unit = {
    println(
      InetAddress.getLocalHost.getHostAddress
    )


    import toolbox6.ssh.SshTools._
    implicit val session = connect

    val address =
      SshTools
        .execValue(
          "echo $SSH_CLIENT",
          { channel =>
            Source
              .fromInputStream(channel.getInputStream)
              .mkString
          }
        )
        .split("\\s+")(0)


    println(address)

    session.disconnect()

  }

}
