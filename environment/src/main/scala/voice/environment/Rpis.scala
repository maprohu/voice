package voice.environment

import ammonite.ops._
import toolbox6.ssh.SshTools
import toolbox8.jartree.common.JarTreeApp
import voice.common.SshConnectionDetails

/**
  * Created by martonpapp on 19/10/16.
  */
object Rpis {
  case class Config(
    host: String,
    servicePort : Int = 8767,
    serviceUser: String = "voicer",
    sshPort: Int = 22,
    user: String = "pi",
    hostKey: Array[Byte] = Array(),
    key: Path = home / ".ssh" / "id_rsa",
    bindAddress: String = "0.0.0.0"
  ) extends SshTools.Config with JarTreeApp.Config {
    def tunneled = copy(host = "localhost")
  }

  implicit val Localhost = Config(
    host = "localhost",
    servicePort = 9981
  )
  val Home = Config(
//    host = "192.168.1.36"
    host = "172.24.1.1",
    servicePort = 33001
  )
  val MobileCable = Config(
    host = "10.1.1.49",
    servicePort = 33002
  )
  val MobileHomeWlan = MobileCable.copy(
    host = "192.168.10.215"
  )
  lazy val Central = {
    val ssh = SshConnectionDetails.local(Sshs.Central)
    import ssh._
    Config(
      host = address,
      sshPort = port,
      user = user,
      key = Path(key),
      bindAddress = "127.0.0.1",
      hostKey = hostKey
    )
  }

}

object Sshs {
  val Central = "central"

}
