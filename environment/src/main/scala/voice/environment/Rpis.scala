package voice.environment

import ammonite.ops._
import toolbox8.installer.SshTools
import toolbox8.jartree.common.JarTreeApp
import voice.common.SshConnectionDetails

/**
  * Created by martonpapp on 19/10/16.
  */
object Rpis {
  case class Config(
    host: String,
    servicePort : Int = 8767,
    sshPort: Int = 22,
    user: String = "pi",
    key: Path = home / ".ssh" / "id_rsa",
    bindAddress: String = "0.0.0.0"
  ) extends SshTools.Config with JarTreeApp.Config

  implicit val Localhost = Config(
    host = "localhost",
    servicePort = 9981
  )
  implicit val Home = Config(
//    host = "192.168.1.36"
    host = "172.24.1.1"
  )
  implicit val MobileCable = Config(
    host = "10.1.1.49"
  )
  implicit val MobileHomeWlan = Config(
    host = "192.168.10.215"
  )
  val Central = {
    val ssh = SshConnectionDetails.local("central")
    import ssh._
    Config(
      host = address,
      sshPort = port,
      user = user,
      key = Path(key),
      bindAddress = "127.0.0.1"
    )
  }

}
