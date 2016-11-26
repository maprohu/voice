package voice.request

import com.typesafe.scalalogging.LazyLogging
import toolbox8.jartree.streamapp.{Plugged, Requestable}

/**
  * Created by maprohu on 26-11-2016.
  */
object GenerateKey {
  case class Input(
    keyLocation: String
  )
  case class Output(
    publicKey: String
  )
}
import GenerateKey._
class GenerateKey extends Requestable[Plugged, Input, Output] with LazyLogging {
  override def request(ctx: Plugged, input: Input): Output = {
    import ammonite.ops._
    import ImplicitWd._
    import input._

    val pub =
      Path(s"${keyLocation}.pub")

    if (!pub.toIO.exists()) {
      logger.info("generating ssh key")
      %%(
        "/usr/bin/ssh-keygen",
        "-q",
        "-t", "rsa",
        "-P", "",
        "-f", keyLocation
      )
    }

    Output(
      publicKey = read(pub)
    )
  }
}
