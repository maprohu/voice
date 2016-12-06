package voice.testing

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Tcp
import toolbox6.ssh.SshTools
import toolbox8.jartree.request.StreamAppRequest
import voice.api.updateclientinfo.ClientInfo
import voice.environment.Rpis

/**
  * Created by pappmar on 25/11/2016.
  */
object RunTunnelHome {

  val homeAddress =
    "85.247.199.73"

  def main(args: Array[String]): Unit = {


    val homeRemote =
      Rpis.Home.remote(
        homeAddress
      )

    SshTools
      .tunnels(
        forward = Seq(
          Rpis.Home.servicePort
        )
      )(
        homeRemote
      )

    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()

    Tcp()
      .bind(
        "127.0.0.1",
        Rpis.Home.directPort
      )
      .runForeach({ c =>
        Tcp()
          .outgoingConnection(
            homeAddress,
            homeRemote.sshPort
          )
          .join(c.flow)
          .run()
      })
  }

}
