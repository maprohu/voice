package voice.environment

import ammonite.ops._
import toolbox6.ssh.SshTools
import toolbox8.jartree.common.JarTreeApp
import voice.common.SshConnectionDetails

/**
  * Created by martonpapp on 19/10/16.
  */
object RpiInstances extends Enumeration {
  val

    Central,
    Home,
    LocalHost,
    Mobile

  = Value
}

object Rpis {


  val servicePortBase = 33000

  def servicePortFor(
    rpi: RpiInstances.Value
  ) : Int = servicePortFor(rpi.id)

  def servicePortFor(
    id: Int
  ) : Int = servicePortBase + id

  case class Config(
    name: String,
    id: Int,
    servicePort : Int,
    host: String = "localhost",
    serviceUser: String = "voicer",
    sshPort: Int = 22,
    user: String = "pi",
    hostKey: Array[Byte] = Array(),
    key: Path = home / ".ssh" / "id_rsa",
    bindAddress: String = "0.0.0.0"
  ) extends SshTools.Config with JarTreeApp.Config {
    def tunneled = copy(host = "localhost")

    def this(
      name: String,
      id: Int,
      bindAddress: String
    ) = {
      this(
        name = name,
        id = id,
        servicePort = servicePortFor(id),
        bindAddress = bindAddress
      )
    }

    def this(
      rpiInstance: RpiInstances.Value,
      bindAddress: String
    ) = {
      this(
        name = rpiInstance.toString,
        id = rpiInstance.id,
        bindAddress = bindAddress
      )
    }
  }

  object Config {
    def apply(
      rpiInstance: RpiInstances.Value
    ): Config = {
      new Config(
        rpiInstance,
        "0.0.0.0"
      )
    }
  }

  implicit val Localhost =
    Config
      .apply(
        RpiInstances.LocalHost
      )

  val Home =
    Config
      .apply(
        RpiInstances.Home
      )

  val Mobile = new Config(
    RpiInstances.Mobile,
    "0.0.0.0"
  ) {
    def cable = this.copy(
      host = "10.1.1.49"
    )
    def home = this.copy(
      host = "192.168.10.215"
    )
  }

  val Central = new Config(
    RpiInstances.Central,
    bindAddress = "127.0.0.1"
  ) {
    def remote = {
      val ssh = SshConnectionDetails.local(Sshs.Central)

      copy(
        host = ssh.address,
        sshPort = ssh.port,
        user = ssh.user,
        key = Path(ssh.key),
        hostKey = ssh.hostKey
      )
    }
  }


}

object Sshs {
  val Central = "central"

}
