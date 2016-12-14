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
  val directPortBase = 32000

  def directPortFor(
    rpi: RpiInstances.Value
  ) : Int = directPortFor(rpi.id)

  def directPortFor(
    id: Int
  ) : Int = directPortBase + id

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
    directPort: Int,
    host: String = "localhost",
    processUser: String = "voicer",
    connectUser: String = "voicer",
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
        directPort = directPortFor(id),
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

  val Home = new Config(
    RpiInstances.Home,
    "0.0.0.0"
  ) {
    val base =
      copy(
        processUser = "root"
      )

    lazy val ssh =
      SshConnectionDetails.local(name.toLowerCase())

    lazy val withKey = {
      base.copy(
        hostKey = ssh.hostKey
      )
    }

    def eth = withKey.copy(
      host = "192.168.1.36"
    )
    def wlan = withKey.copy(
      host = "172.24.1.1"
    )

    def remote(
      ip: String
    ) = {
      withKey
        .copy(
          host = ip,
          user = ssh.user,
          sshPort = ssh.port,
          key = Path(ssh.key)
        )
    }

    def direct = {
      withKey
        .copy(
          host = "localhost",
          user = ssh.user,
          sshPort = directPort,
          key = Path(ssh.key)
        )
    }

  }

  val Mobile = new Config(
    RpiInstances.Mobile,
    "0.0.0.0"
  ) {
    lazy val ssh =
      SshConnectionDetails.local(name.toLowerCase())

    val base = copy(
      processUser = "root"
    )

    lazy val withKey = {
      base.copy(
        hostKey = ssh.hostKey
      )
    }

    def cable = withKey.copy(
      host = "10.1.1.49"
    )
    def homeCable = withKey.copy(
      host = "192.168.1.37"
    )
    def home = withKey.copy(
      host = "192.168.10.215"
    )
  }

  val Central = new Config(
    RpiInstances.Central,
    bindAddress = "127.0.0.1"
  ) {
    def remote = {
      val ssh = SshConnectionDetails.local(name.toLowerCase())

      copy(
        host = ssh.address,
        sshPort = ssh.port,
        user = ssh.user,
        key = Path(ssh.key),
        hostKey = ssh.hostKey,
        processUser = "root"
      )
    }
  }


}

//object Sshs {
//  val Central = "central"
//
//}
