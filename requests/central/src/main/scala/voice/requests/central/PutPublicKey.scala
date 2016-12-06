package voice.requests.central

import java.io.{InputStream, ObjectInputStream, OutputStream}
import java.util

import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.central.CentralPlugged
import ammonite.ops._
import com.typesafe.scalalogging.StrictLogging

import scala.collection.JavaConversions._

/**
  * Created by maprohu on 26-11-2016.
  */
object PutPublicKey {
  @SerialVersionUID(1)
  case class Input(
    client: String,
    publicKey: String
  )
}
import PutPublicKey._
class PutPublicKey extends Requestable with StrictLogging {

  override def request(rctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    val dis = new ObjectInputStream(in)
    val data =
      dis
        .readObject()
        .asInstanceOf[Input]

    val ctx =
      rctx
        .holder
        .get
        .plugged
        .asInstanceOf[CentralPlugged]

    import data._
    ctx.publicKeys.synchronized {
      logger.info(s"adding public key: ${publicKey.trim}")
      ctx.publicKeys.put(client, publicKey.trim)
      ctx.db.commit()

      val authorizedKeys = root / 'home / 'voicer / ".ssh" / 'authorized_keys
      logger.info(s"regenerating ${authorizedKeys}")
      write.over(
        authorizedKeys,
        ctx
          .publicKeys
          .values()
          .mkString("", "\n", "\n")
      )
    }
  }
}
