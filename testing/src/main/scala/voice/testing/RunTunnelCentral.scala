package voice.testing

import toolbox8.installer.SshTools
import voice.environment.Rpis

/**
  * Created by pappmar on 25/11/2016.
  */
object RunTunnelCentral {

  def main(args: Array[String]): Unit = {
    SshTools
      .tunnels(
        forward = Seq(Rpis.Central.servicePort),
        reverse = Seq(3000)
      )(Rpis.Central)
  }

}
