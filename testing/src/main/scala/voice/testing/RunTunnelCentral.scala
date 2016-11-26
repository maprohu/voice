package voice.testing

import toolbox6.ssh.SshTools
import voice.environment.Rpis

/**
  * Created by pappmar on 25/11/2016.
  */
object RunTunnelCentral {

  def main(args: Array[String]): Unit = {
    SshTools
      .tunnels(
        forward = Seq(
          Rpis.Central.servicePort,
          Rpis.Home.servicePort
        ),
        reverse = Seq(
          3000
        )
      )(Rpis.Central)
  }

}
