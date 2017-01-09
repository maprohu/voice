package voice.testing

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Tcp
import toolbox6.ssh.SshTools
import toolbox6.ssh.SshTools.ForwardTunnel
import toolbox8.jartree.request.StreamAppRequest
import voice.api.updateclientinfo.ClientInfo
import voice.environment.Rpis

/**
  * Created by pappmar on 25/11/2016.
  */
object RunTunnelCentralWithHome {

  def main(args: Array[String]): Unit = {
    SshTools
      .tunnels(
        forward = Seq(
          Rpis.Central.servicePort
        ),
        reverse = Seq(
          Rpis.Localhost.servicePort
        )
      )(Rpis.Central.remote)

    val homeAddress =
      StreamAppRequest
        .request(
          ClientInfo.Read,
          Rpis.Home.id,
          Rpis.Central.tunneled
        )
        .get
        .publicAddress

    val homeRemote =
      Rpis.Home.remote(
        homeAddress
      )

    SshTools
      .tunnels(
        forward = Seq(
          Rpis.Home.servicePort,
          7272,
          ForwardTunnel(
            Rpis.Mobile.servicePort,
            Rpis.Mobile.homeCable.host,
            Rpis.Mobile.servicePort
          )
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

/*

ultimate schema

- programming without text files -> syntax and formatting are not part of the program
- possible voice controlled editing
- eventually everything is represented as a sequence of bytes
- multiple replicas, offline editing, easy synchronization
- each replica needs a unique id


taking notes
- on paper
- paper creates grouping
- you see related notes together
- the time of taking the note should help to group them
- it should be possible to create connections between notes
- qualify the connection between notes with further notes
- notes can be organized into a graph
- notes can be themselves nodes in that graph
- edges can be labeled with notes
- trying to represent "thought" digitally
- does that make sense?
- is there any benefit?
is it not just the end of the thinking process that really matters?
let's assume it makes sense
how could a thought be best described?
what language could be used?
English?
or some made up language?






 */
