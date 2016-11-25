package voice.common

/**
  * Created by pappmar on 25/11/2016.
  */
case class SshConnectionDetails(
  address: String,
  user: String,
  port: Int,
  key: String
)

object SshConnectionDetails {
  def local(name: String) = {
    import ammonite.ops._
    upickle.default.read[SshConnectionDetails](
      read(pwd / up / 'voice / 'local / s"${name}.json")
    )
  }


}


