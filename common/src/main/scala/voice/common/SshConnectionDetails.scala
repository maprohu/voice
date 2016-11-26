package voice.common

/**
  * Created by pappmar on 25/11/2016.
  */
case class SshConnectionDetails(
  address: String,
  user: String,
  port: Int,
  key: String,
  hostKey: Array[Byte]
)

object SshConnectionDetails {
  def jsonName(name: String) = {
    s"${name}.json"
  }
  def localPath(name: String) = {
    import ammonite.ops._
    pwd / up / 'voice / 'local / jsonName(name)
  }
  def local(name: String) = {
    import ammonite.ops._
    unpickle(
      read(localPath(name))
    )
  }
  def unpickle(json: String) = {
    upickle.default.read[SshConnectionDetails](
      json
    )
  }


}


