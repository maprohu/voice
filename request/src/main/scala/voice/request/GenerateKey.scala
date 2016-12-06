package voice.request

import java.io.{InputStream, ObjectInputStream, ObjectOutputStream, OutputStream}

import com.typesafe.scalalogging.LazyLogging
import toolbox8.jartree.streamapp.{Plugged, Requestable, RootContext}

/**
  * Created by maprohu on 26-11-2016.
  */
object GenerateKey {
  @SerialVersionUID(0)
  case class Input(
    keyLocation: String
  )
  @SerialVersionUID(0)
  case class Output(
    publicKey: String
  )
}
import GenerateKey._
class GenerateKey extends Requestable with LazyLogging {


  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    import ammonite.ops._
    import ImplicitWd._
    val dis = new ObjectInputStream(in)
    val input =
      dis
        .readObject()
        .asInstanceOf[Input]

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

    val dos = new ObjectOutputStream(out)
    dos.writeObject(
      Output(
        publicKey = read(pub)
      )
    )
    dos.flush()
  }
}
